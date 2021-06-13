package com.book.manager.application.service

import com.book.manager.domain.model.BookWithRental
import com.book.manager.domain.repository.BookRepository
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class BookService(
    private val bookRepository: BookRepository
) {
    fun getList(): List<BookWithRental> {
        return bookRepository.findAllWithRental()
    }

    fun getDetail(bookId: Long): BookWithRental {
        // ?: エルビス演算子 結果がnullだったら例外を投げる例に便利 if (result == null)の書き換え
        return bookRepository.findWithRental(bookId) ?: throw IllegalArgumentException("存在しない書籍ID: $bookId")
    }
}