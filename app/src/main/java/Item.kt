data class Item(
    val id: Int,
    val name: String,
    val price: Double,
    val sizes: List<String>,
    val type: ItemType // Enum representing different types of items
)

enum class ItemType {
    SHIRT,
    PANTS,
    SKIRT,
    DRESS,
    SPORTSWEAR,
    SWIMWEAR,
    ACCESSORY,
    SHOES
}