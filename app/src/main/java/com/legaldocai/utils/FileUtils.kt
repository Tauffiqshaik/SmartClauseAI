package com.legaldocai.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import timber.log.Timber
import java.io.BufferedReader
import java.io.InputStreamReader

object FileUtils {

    fun getFileName(context: Context, uri: Uri): String? {
        return try {
            context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                cursor.moveToFirst()
                cursor.getString(nameIndex)
            }
        } catch (e: Exception) {
            uri.lastPathSegment
        }
    }

    fun getFileSize(context: Context, uri: Uri): String {
        return try {
            context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                cursor.moveToFirst()
                val bytes = cursor.getLong(sizeIndex)
                formatFileSize(bytes)
            } ?: "Unknown"
        } catch (e: Exception) {
            "Unknown"
        }
    }

    private fun formatFileSize(bytes: Long): String {
        return when {
            bytes < 1024 -> "$bytes B"
            bytes < 1024 * 1024 -> "${bytes / 1024} KB"
            else -> String.format("%.1f MB", bytes / (1024.0 * 1024.0))
        }
    }

    fun extractTextFromUri(context: Context, uri: Uri): String {
        val mimeType = context.contentResolver.getType(uri)
        return try {
            when {
                mimeType == "application/pdf" || uri.toString().endsWith(".pdf") -> {
                    extractPdfText(context, uri)
                }
                mimeType?.startsWith("text/") == true -> {
                    extractPlainText(context, uri)
                }
                else -> extractPlainText(context, uri)
            }
        } catch (e: Exception) {
            Timber.e(e, "Error extracting text from file")
            ""
        }
    }

    private fun extractPdfText(context: Context, uri: Uri): String {
        return context.contentResolver.openInputStream(uri)?.use { stream ->
            PDDocument.load(stream).use { document ->
                PDFTextStripper().getText(document)
            }
        } ?: ""
    }

    private fun extractPlainText(context: Context, uri: Uri): String {
        return context.contentResolver.openInputStream(uri)?.use { stream ->
            BufferedReader(InputStreamReader(stream)).readText()
        } ?: ""
    }
}
