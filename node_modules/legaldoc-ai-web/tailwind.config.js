/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: "#0A1628",
        secondary: "#C9A84C",
        accent: "#64FFDA",
        surface: "#112240",
      },
    },
  },
  plugins: [],
}
