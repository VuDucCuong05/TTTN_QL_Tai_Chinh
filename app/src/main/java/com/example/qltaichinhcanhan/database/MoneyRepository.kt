package com.example.qltaichinhcanhan.database

import androidx.lifecycle.LiveData
import com.example.qltaichinhcanhan.mode.Money

class MoneyRepository(private val moneyDao: MoneyDao) {
    val readAllData: LiveData<List<Money>> = moneyDao.readAllData()

    fun addBook(money: Money) {
        return moneyDao.addBook(money)
    }

    fun updateBook(money: Money) {
        moneyDao.updateBook(money)
    }

    fun deleteBook(money: Money) {
        moneyDao.deleteBook(money)
    }

    fun deleteAllBook() {
        moneyDao.deleteAllBook()
    }
}