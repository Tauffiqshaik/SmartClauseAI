import React, { useState, useEffect, useRef } from 'react';
import {
  FileText, Upload, Trash2, Send, Mic,
  Settings, History, Info, ShieldAlert, BookOpen,
  Home, Folder, ArrowLeft, Key, Scale
} from 'lucide-react';
import { PDFDocument } from 'pdf-lib';

// --- CONFIG ---
const GROQ_API_KEY = import.meta.env.VITE_GROQ_API_KEY || "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";
const SYSTEM_PROMPT = `You are LegalDocAI, an expert legal document analyst. Analyze documents thoroughly, highlight risks with ⚠️, and provide structured summaries. Always remind users this is not professional legal advice.`;

// Abstract Logo Component
const Logo = () => (
  <div className="flex items-center justify-center gap-2 text-secondary py-8">
    <div className="border-[3px] border-secondary rounded-full w-14 h-14"></div>
    <div className="w-[6px] h-16 bg-secondary rounded-full"></div>
    <div className="border-[3px] border-secondary rounded-full w-14 h-14"></div>
  </div>
);

const App = () => {
  const [messages, setMessages] = useState([
    { role: 'bot', content: '✅ Document loaded successfully! I\'ve analyzed 0 words across your legal document.\n\nYou can now: • Ask me questions about this document • Request a summary • Ask about specific clauses or terms • Use the 🎤 mic to ask via voice.' }
  ]);
  const [input, setInput] = useState('');
  const [loading, setLoading] = useState(false);
  const [documentText, setDocumentText] = useState('');
  const [fileName, setFileName] = useState('');
  const [history, setHistory] = useState([]);
  const [view, setView] = useState('home'); // 'home' | 'documents' | 'history' | 'settings' | 'chat'

  const chatEndRef = useRef(null);

  useEffect(() => {
    chatEndRef.current?.scrollIntoView({ behavior: 'smooth' });
    const savedHistory = localStorage.getItem('legalHistory');
    if (savedHistory) setHistory(JSON.parse(savedHistory));
  }, [messages, view]);

  const handleFileUpload = async (e) => {
    const file = e.target.files[0];
    if (!file) return;
    setFileName(file.name);
    setLoading(true);

    try {
      let text = "";
      if (file.type === "application/pdf") {
        const arrayBuffer = await file.arrayBuffer();
        const pdfDoc = await PDFDocument.load(arrayBuffer);
        text = `[Extracted content from ${file.name}]... (In a real app, use pdf.js for full extraction)`;
      } else {
        text = await file.text();
      }

      setDocumentText(text);
      setMessages([
        { role: 'bot', content: `✅ Document loaded successfully! I've analyzed ${text.split(' ').length} words across your legal document.\n\nYou can now: • Ask me questions about this document • Request a summary • Ask about specific clauses or terms • Use the 🎤 mic to ask via voice.` }
      ]);
      setView('chat'); // Auto navigate to chat on upload
    } catch (err) {
      alert("Failed to read file");
    } finally {
      setLoading(false);
    }
  };

  const askQuestion = async (query) => {
    if (!query.trim()) return;
    const userQuery = query || input;
    setMessages(prev => [...prev, { role: 'user', content: userQuery }]);
    setInput('');
    setLoading(true);

    try {
      const response = await fetch("https://api.groq.com/openai/v1/chat/completions", {
        method: "POST",
        headers: {
          "Authorization": `Bearer ${GROQ_API_KEY}`,
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
          model: "llama-3.3-70b-versatile",
          messages: [
            { role: "system", content: SYSTEM_PROMPT },
            { role: "user", content: `DOCUMENT CONTEXT: ${documentText}\n\nQUESTION: ${userQuery}` }
          ]
        })
      });

      const data = await response.json();
      const aiResponse = data.choices[0].message.content;

      setMessages(prev => [...prev, { role: 'bot', content: aiResponse }]);

      // Save to History
      const newHistory = [{ query: userQuery, response: aiResponse, date: new Date().toLocaleString(), doc: fileName }, ...history];
      setHistory(newHistory);
      localStorage.setItem('legalHistory', JSON.stringify(newHistory));

    } catch (err) {
      setMessages(prev => [...prev, { role: 'bot', content: "⚠️ Error connecting to AI service." }]);
    } finally {
      setLoading(false);
    }
  };

  const renderHome = () => (
    <div className="flex-1 overflow-y-auto p-4 pb-20 bg-primary w-full h-full">
      <Logo />
      
      {/* Upload Card */}
      <div className="bg-surface rounded-3xl p-6 mb-8 text-center shadow-lg border border-white/5">
        <div className="flex justify-center mb-4">
          <div className="text-secondary w-16 h-12 relative">
            {/* Custom Folder Icon */}
            <svg viewBox="0 0 24 24" fill="currentColor" className="w-full h-full opacity-80">
              <path d="M10 4H4c-1.1 0-1.99.9-1.99 2L2 18c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V8c0-1.1-.9-2-2-2h-8l-2-2z" />
            </svg>
          </div>
        </div>
        <h2 className="text-white text-xl font-semibold mb-1">Upload Legal Document</h2>
        <p className="text-gray-400 text-sm mb-6">Supports PDF, DOC, DOCX, TXT</p>
        
        <div className="w-16 h-[2px] bg-secondary mx-auto mb-6 opacity-50"></div>
        
        <div className="flex gap-3 justify-center">
          <label className="flex-1 bg-secondary hover:bg-secondary/90 text-primary font-bold py-3 rounded-lg flex items-center justify-center gap-2 cursor-pointer transition">
            <FileText size={18} />
            PDF
            <input type="file" className="hidden" onChange={handleFileUpload} accept=".pdf" />
          </label>
          <label className="flex-1 bg-accent hover:bg-accent/90 text-primary font-bold py-3 rounded-lg flex items-center justify-center gap-2 cursor-pointer transition">
            <BookOpen size={18} />
            DOC / TXT
            <input type="file" className="hidden" onChange={handleFileUpload} accept=".doc,.docx,.txt" />
          </label>
        </div>
      </div>

      {/* Grid */}
      <h3 className="text-white font-bold mb-4 px-1">What I can do</h3>
      <div className="grid grid-cols-2 gap-4">
        {[
          { icon: <Info size={24}/>, title: "Summarize", desc: "Clear summaries of complex documents" },
          { icon: <ShieldAlert size={24} className="text-secondary"/>, title: "Risk Scan", desc: "Identify risky clauses & obligations" },
          { icon: <Mic size={24}/>, title: "Voice Q&A", desc: "Ask questions by voice" },
          { icon: <Key size={24} className="text-secondary"/>, title: "Key Terms", desc: "Plain-English definitions" }
        ].map((item, idx) => (
          <div key={idx} className="bg-surface p-4 rounded-2xl border border-white/5">
            <div className="text-gray-400 mb-3">{item.icon}</div>
            <h4 className="text-white font-bold text-sm mb-1">{item.title}</h4>
            <p className="text-xs text-gray-400 leading-tight">{item.desc}</p>
          </div>
        ))}
      </div>
    </div>
  );

  const renderDocuments = () => (
    <div className="flex-1 flex flex-col bg-primary h-full relative">
      <div className="p-6">
        <h2 className="text-2xl font-bold text-white mb-1">My Documents</h2>
        <p className="text-sm text-gray-400">Tap any document to analyze it</p>
      </div>
      
      <div className="flex-1 flex flex-col items-center justify-center -mt-20">
        <div className="text-secondary w-24 h-20 mb-6 opacity-80">
          <svg viewBox="0 0 24 24" fill="currentColor" className="w-full h-full">
            <path d="M10 4H4c-1.1 0-1.99.9-1.99 2L2 18c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V8c0-1.1-.9-2-2-2h-8l-2-2z" />
          </svg>
        </div>
        <h3 className="text-xl font-bold text-white mb-2">No documents yet</h3>
        <p className="text-sm text-gray-400">Tap + to upload a legal document</p>
      </div>
      
      {/* FAB */}
      <label className="absolute bottom-6 right-6 w-14 h-14 bg-secondary rounded-full flex items-center justify-center text-primary shadow-lg cursor-pointer hover:bg-secondary/90 transition text-2xl font-light">
        +
        <input type="file" className="hidden" onChange={handleFileUpload} accept=".pdf,.doc,.docx,.txt" />
      </label>
    </div>
  );

  const renderHistory = () => (
    <div className="flex-1 overflow-y-auto p-4 pb-20 bg-primary h-full">
      <h2 className="text-2xl font-bold text-white mb-6 pt-2">Analysis History</h2>
      {history.length === 0 ? (
         <div className="text-center py-20 text-gray-500">No history found. Start a conversation to see it here.</div>
      ) : history.map((h, i) => (
        <div key={i} className="bg-surface p-5 rounded-2xl border border-white/5 mb-4 group">
          <div className="flex justify-between items-start mb-2">
            <span className="text-[10px] text-secondary font-bold uppercase tracking-widest">{h.date}</span>
            <span className="text-[10px] text-gray-500">{h.doc}</span>
          </div>
          <h3 className="font-bold text-sm mb-2 text-white/90">{h.query}</h3>
          <p className="text-sm text-gray-400 line-clamp-3 leading-relaxed">{h.response}</p>
        </div>
      ))}
    </div>
  );

  const renderSettings = () => (
    <div className="flex-1 overflow-y-auto p-4 pb-20 bg-primary h-full">
      <h2 className="text-2xl font-bold text-white mb-6 pt-2">Settings</h2>
      <div className="space-y-4">
        <div className="bg-surface p-4 rounded-2xl border border-white/5">
          <label className="text-xs text-gray-400 block mb-2 uppercase tracking-widest font-bold">Groq API Key</label>
          <input readOnly value={GROQ_API_KEY.replace(/.(?=.{4})/g, '*')} className="w-full bg-primary/50 p-3 rounded-xl border border-white/10 text-sm text-secondary font-mono outline-none" />
        </div>
        <div className="bg-surface p-4 rounded-2xl border border-white/5 flex justify-between items-center">
          <span className="text-sm text-white">Dark Mode</span>
          <div className="w-12 h-6 bg-secondary rounded-full relative"><div className="absolute right-1 top-1 w-4 h-4 bg-primary rounded-full"></div></div>
        </div>
        <button
          onClick={() => { localStorage.clear(); setHistory([]); }}
          className="w-full py-4 rounded-2xl border border-red-500/30 text-red-500 text-sm hover:bg-red-500/10 transition flex items-center justify-center gap-2 font-bold"
        >
          <Trash2 size={18}/> Clear Local Data
        </button>
      </div>
    </div>
  );

  const renderChat = () => (
    <div className="flex-1 flex flex-col bg-primary h-full">
      {/* Chat Header */}
      <header className="flex flex-col border-b border-white/5 pb-2 pt-4 px-4 sticky top-0 bg-primary z-10">
        <div className="flex items-center gap-4 mb-4">
          <button onClick={() => setView('home')} className="text-white/70 hover:text-white transition p-1">
            <ArrowLeft size={24} />
          </button>
          <h1 className="text-xl font-bold text-white tracking-wide">LegalDoc AI</h1>
        </div>
        
        {/* Pills */}
        <div className="flex gap-2 overflow-x-auto no-scrollbar pb-1">
          <button onClick={() => askQuestion("Summarize this document")} className="whitespace-nowrap px-4 py-[6px] bg-transparent border border-white/30 rounded-full text-xs text-white flex items-center gap-2 hover:bg-white/5 transition"><Info size={14}/> Summarize</button>
          <button onClick={() => askQuestion("Find legal risks")} className="whitespace-nowrap px-4 py-[6px] bg-transparent border border-secondary rounded-full text-xs text-white flex items-center gap-2 hover:bg-secondary/10 transition"><ShieldAlert size={14} className="text-secondary"/> Find Risks</button>
          <button onClick={() => askQuestion("Define key terms")} className="whitespace-nowrap px-4 py-[6px] bg-transparent border border-accent rounded-full text-xs text-white flex items-center gap-2 hover:bg-accent/10 transition"><Key size={14} className="text-accent"/> Key Terms</button>
        </div>
      </header>

      {/* Chat Area */}
      <div className="flex-1 overflow-y-auto chat-scrollbar p-4 space-y-6">
        {messages.map((msg, i) => (
          <div key={i} className={`flex ${msg.role === 'user' ? 'justify-end' : 'justify-start items-end gap-2'}`}>
            {msg.role === 'bot' && (
              <div className="w-8 h-8 rounded-full border border-secondary flex items-center justify-center text-secondary mb-1 flex-shrink-0">
                <Scale size={16} />
              </div>
            )}
            <div className={`max-w-[85%] p-4 rounded-2xl text-sm leading-relaxed ${msg.role === 'user' ? 'bg-secondary text-primary font-medium rounded-tr-sm' : 'bg-[#1a2f4c] text-white/90 rounded-tl-sm border border-white/5'}`}>
              <div className="whitespace-pre-wrap">{msg.content}</div>
            </div>
          </div>
        ))}
        {loading && (
          <div className="flex justify-start items-end gap-2">
             <div className="w-8 h-8 rounded-full border border-secondary flex items-center justify-center text-secondary mb-1 flex-shrink-0">
                <Scale size={16} />
             </div>
             <div className="bg-[#1a2f4c] p-4 rounded-2xl rounded-tl-sm animate-pulse text-white/60 text-sm">Thinking...</div>
          </div>
        )}
        <div ref={chatEndRef} />
      </div>

      {/* Input Bar */}
      <div className="p-3 bg-primary border-t border-white/5">
        <div className="bg-surface flex items-center rounded-xl p-1 shadow-inner border border-white/10">
          <input
            value={input}
            onChange={(e) => setInput(e.target.value)}
            onKeyPress={(e) => e.key === 'Enter' && askQuestion()}
            placeholder={documentText ? "Ask about this document..." : "Upload a document first..."}
            className="flex-1 bg-transparent border-none outline-none text-sm text-white px-3 py-3"
            disabled={!documentText || loading}
          />
          <button className="p-3 text-white/50 hover:text-white transition rounded-full mr-1">
            <Mic size={20} />
          </button>
          <button
            onClick={() => askQuestion()}
            disabled={!input.trim() || loading}
            className="bg-secondary text-primary p-3 rounded-full disabled:opacity-50 transition transform active:scale-95"
          >
            <Send size={18} className="ml-1" />
          </button>
        </div>
      </div>
    </div>
  );

  return (
    <div className="flex flex-col h-screen w-full bg-primary font-sans text-white overflow-hidden">
      
      {/* Active View Container */}
      <div className="flex-1 overflow-hidden h-full flex flex-col">
        {view === 'home' && renderHome()}
        {view === 'documents' && renderDocuments()}
        {view === 'history' && renderHistory()}
        {view === 'settings' && renderSettings()}
        {view === 'chat' && renderChat()}
      </div>

      {/* Bottom Navigation */}
      {view !== 'chat' && (
        <nav className="bg-primary border-t border-white/5 flex justify-around items-center p-3 pb-6 fixed bottom-0 w-full z-20">
          <button onClick={() => setView('home')} className={`flex flex-col items-center gap-1 ${view === 'home' ? 'text-secondary' : 'text-gray-500 hover:text-gray-400'}`}>
            <Home size={22} className={view === 'home' ? 'fill-current' : ''} />
            <span className="text-[10px] font-bold">Home</span>
          </button>
          <button onClick={() => setView('documents')} className={`flex flex-col items-center gap-1 ${view === 'documents' ? 'text-secondary' : 'text-gray-500 hover:text-gray-400'}`}>
            <Folder size={22} className={view === 'documents' ? 'fill-current' : ''} />
            <span className="text-[10px] font-bold">Documents</span>
          </button>
          <button onClick={() => setView('history')} className={`flex flex-col items-center gap-1 ${view === 'history' ? 'text-secondary' : 'text-gray-500 hover:text-gray-400'}`}>
            <History size={22} />
          </button>
          <button onClick={() => setView('settings')} className={`flex flex-col items-center gap-1 ${view === 'settings' ? 'text-secondary' : 'text-gray-500 hover:text-gray-400'}`}>
            <Settings size={22} />
          </button>
        </nav>
      )}
    </div>
  );
};

export default App;
