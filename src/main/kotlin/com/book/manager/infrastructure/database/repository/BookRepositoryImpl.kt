package com.book.manager.infrastructure.database.repository

import com.book.manager.domain.model.Book
import com.book.manager.domain.model.BookWithRental
import com.book.manager.domain.model.Rental
import com.book.manager.domain.repository.BookRepository
import com.book.manager.infrastructure.database.mapper.BookWithRentalMapper
import com.book.manager.infrastructure.database.mapper.select
import com.book.manager.infrastructure.database.mapper.selectByPrimaryKey
import com.book.manager.infrastructure.database.record.BookWithRentalRecord
import org.springframework.stereotype.Repository

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@Repository
class BookRepositoryImpl(
    private val bookWithRentalMapper: BookWithRentalMapper
) : BookRepository {
    override fun findAllWithRental(): List<BookWithRental> {
        return bookWithRentalMapper.select().map { toModel(it) }
    }

    override fun findWithRental(id: Long): BookWithRental? {
        // 安全呼び出し let -> 取得できなかったらnullを返す　if (result != null)の書き換え
        return bookWithRentalMapper.selectByPrimaryKey(id)?.let { toModel(it) }
    }

    private fun toModel(record: BookWithRentalRecord): BookWithRental {
        val book = Book(
            record.id!!,
            record.title!!,
            record.author!!,
            record.releaseDate!!
        )
        val rental = Rental(
            record.id!!,
            record.userId!!,
            record.rentalDatetime!!,
            record.returnDeadline!!
        )
        return BookWithRental(book, rental)
    }
}