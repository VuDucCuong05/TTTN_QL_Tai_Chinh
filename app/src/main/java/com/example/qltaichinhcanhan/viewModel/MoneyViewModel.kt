package com.example.qltaichinhcanhan.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.qltaichinhcanhan.database.MoneyDatabase
import com.example.qltaichinhcanhan.database.MoneyRepository
import com.example.qltaichinhcanhan.mode.Money
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoneyViewModel(application: Application) : AndroidViewModel(application) {

    private var db = MoneyDatabase.getInstance(application)
    private var bookDao = db.moneyDao()

    private val repository: MoneyRepository = MoneyRepository(bookDao)
    val readAllData = repository.readAllData

    fun addBook(money: Money) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addBook(money)
        }
    }

    fun updateBook(money: Money) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateBook(money)
        }
    }

    fun deleteBook(money: Money) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteBook(money)
        }
    }

    fun deleteAllUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllBook()
        }
    }
}


