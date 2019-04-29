

# DESIGN PATTERNS USED #

* **Singleton Design Pattern**
```kotlin
val user = FirebaseAuth.getInstance()
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