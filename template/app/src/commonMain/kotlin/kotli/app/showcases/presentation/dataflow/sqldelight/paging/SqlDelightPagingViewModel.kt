package kotli.app.showcases.presentation.dataflow.sqldelight.paging

import androidx.lifecycle.viewModelScope
import kotli.app.common.data.source.database.DatabaseSource
import kotli.app.common.data.source.database.UserEntity
import shared.data.source.paging.Pager
import shared.data.source.paging.PagingSource
import shared.presentation.viewmodel.BaseViewModel

class SqlDelightPagingViewModel(
    private val databaseSource: DatabaseSource,
    pagingSource: PagingSource,
) : BaseViewModel() {

    private val pager = pagingSource.createPager(viewModelScope) { limit, offset ->
        databaseSource.getUsers(limit, offset)
    }

    private val _state = SqlDelightPagingMutableState(pager)
    val state: SqlDelightPagingState = _state

    override fun doBind() = async("Init users") {
        val max = 100
        val count = databaseSource.getUsersCount()
        if (count < max) {
            databaseSource.inTx {
                (count until max).forEach { id ->
                    val uid = id + 1
                    val firstName = "first_name_$uid"
                    val lastName = "last_name_$uid"
                    databaseSource.insertUser(firstName, lastName)
                }
            }
            pager.refresh()
        }
    }

    fun onDelete(user: UserEntity) = async("Delete user ${user.id}") {
        databaseSource.deleteUser(user.id)
        pager.refresh()
    }

    private class SqlDelightPagingMutableState(
        override val users: Pager<UserEntity>
    ) : SqlDelightPagingState
}
