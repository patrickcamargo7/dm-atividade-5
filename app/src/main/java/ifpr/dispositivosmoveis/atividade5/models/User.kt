package ifpr.dispositivosmoveis.atividade5.models

data class User(
    var username: String,
    var password: String
) {
    var id: Long? = null

    override fun equals(other: Any?) = other is User && this.id == other.id
}