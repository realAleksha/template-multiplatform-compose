package kotli.app.showcases.presentation.dataflow.room.paging

import androidx.lifecycle.viewModelScope
import kotli.app.common.data.source.database.DatabaseSource
import kotli.app.common.data.source.database.UserEntity
import shared.data.source.paging.Pager
import shared.data.source.paging.PagingSource
import shared.presentation.viewmodel.BaseViewModel

class RoomPagingViewModel(
    private val databaseSource: DatabaseSource,
    pagingSource: PagingSource,
) : BaseViewModel() {

    private val pager = pagingSource.createPager(viewModelScope) { limit, offset ->
        databaseSource.getUsers(limit, offset)
    }

    private val _state = RoomPagingMutableState(pager)
    val state: RoomPagingState = _state

    override fun doBind() = async("doBind") {
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

    private class RoomPagingMutableState(override val pager: Pager<UserEntity>) : RoomPagingState
}
