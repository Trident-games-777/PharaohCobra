package inc.trilokia.gfxtoo.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CobraRepository(private val database: DatabaseReference) {

    suspend fun getSnapshot(): DataSnapshot = suspendCoroutine {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                it.resume(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}