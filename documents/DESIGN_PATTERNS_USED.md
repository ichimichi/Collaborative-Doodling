

# DESIGN PATTERNS USED #

* **Singleton Design Pattern**

```kotlin
val user = FirebaseAuth.getInstance()
```
```kotlin
database = FirebaseDatabase.getInstance()
```

* **Adapter Design Pattern**

```kotlin
class SavedDoodleAdapter : RecyclerView.Adapter<CustomViewHolder>()
{

    val doodleTitles = listOf("doodle 1", "doodle 2", "doodle 3")

    override fun getItemCount(): Int 
    {
        return doodleTitles.size
    }
}

```

```kotlin
saved_doodleRV.adapter = SavedDoodleAdapter()
```
* **Observer Design Pattern**

```kotlin        
newDoodleBtn.setOnClickListener {
    val intent = Intent(this,sessionOptionActivity::class.java)
    startActivity(intent)
}
```

```kotlin
pencilAV.setOnClickListener {
    index = ((index + 1) % strokeList.size )
    pencilAV.setAnimation(strokeList[index])
    pencilAV.resumeAnimation()
}
```

```kotlin
drawingInstruction!!.addValueEventListener( object: ValueEventListener {
    
    override fun onCancelled(error: DatabaseError) {
        Log.w("error", "Failed to read value.", error.toException())
    }

    override fun onDataChange(dataSnapshot: DataSnapshot) {
        val value = dataSnapshot.getValue(Instruction::class.java)
        Log.d("command", value.toString())
        // execute commands
    }
})
 ```