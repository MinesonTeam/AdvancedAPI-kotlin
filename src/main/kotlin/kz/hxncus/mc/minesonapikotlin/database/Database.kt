//package kz.hxncus.mc.minesonapikotlin.database
//
//import com.zaxxer.hikari.HikariDataSource
//import lombok.Getter
//import lombok.ToString
//import org.bukkit.plugin.Plugin
//import org.jooq.impl.DSL
//import java.io.File
//import java.util.*
//
///**
// * The type Database.
// * @author Hxncus
// * @since  1.0.0
// */
//open class Database(
//    plugin: Plugin,
//    /**
//     * The Table SQL.
//     */
//    protected var tableSQL: String?,
//    /**
//     * The Settings.
//     */
//    protected var settings: DatabaseSettings
//) {
//    /**
//     * The Url.
//     */
//    protected var url: String? = null
//
//    /**
//     * The Data source.
//     */
//    protected var dataSource: HikariDataSource? = null
//
//    /**
//     * The Dsl context.
//     */
//    protected var dslContext: DSLContext? = null
//
//    /**
//     * Instantiates a new Database.
//     *
//     * @param plugin   the plugin
//     * @param tableSQL the table SQL
//     * @param settings the settings
//     */
//    init {
//        if (Objects.requireNonNull(settings.getSqlDialect()) === SQLDialect.SQLITE) {
//            this.url =
//                ("jdbc:sqlite:plugins/" + plugin.dataFolder.name + File.separator + settings.getDatabase()).toString() + ".db"
//        } else {
//            this.url = ((("jdbc:" + settings.getSqlDialect()
//                .getNameLC()).toString() + "://" + settings.getHost()).toString() + ":" + settings.getPort()).toString() + File.separator + settings.getDatabase()
//        }
//        this.createConnection()
//    }
//
//    /**
//     * Create a connection.
//     */
//    fun createConnection() {
//        this.dataSource = HikariDataSource()
//        dataSource.setJdbcUrl(this.url)
//        dataSource.setUsername(settings.getUsername())
//        dataSource.setPassword(settings.getPassword())
//        val properties: Map<String, String> = settings.getProperties()
//        if (properties != null) {
//            properties.forEach(dataSource::addDataSourceProperty)
//        }
//        this.dslContext = DSL.using(this.dataSource, settings.getSqlDialect())
//        if (this.tableSQL != null && !tableSQL!!.isEmpty()) {
//            this.execute(this.tableSQL)
//        }
//    }
//
//    /**
//     * Execute int.
//     *
//     * @param sql the SQL
//     * @return the int
//     */
//    fun execute(@NonNull sql: String?): Int {
//        return dslContext.execute(sql)
//    }
//
//    /**
//     * Gets new id.
//     *
//     * @param table the table
//     * @return the new id
//     */
//    fun getNewId(@NonNull table: String): Int {
//        val entry = this.fetchOne("SELECT MAX(id) as maxId FROM $table") ?: return 1
//        val obj: Any = entry.get("maxId")
//        return if (obj == null) 1 else obj as Int + 1
//    }
//
//    /**
//     * Fetch one record.
//     *
//     * @param sql the SQL
//     * @return the record
//     */
//    fun fetchOne(@NonNull sql: String?): Record {
//        return dslContext.fetchOne(sql)
//    }
//
//    /**
//     * Fetch result.
//     *
//     * @param sql the SQL
//     * @return the result
//     */
//    @NonNull
//    fun fetch(@NonNull sql: String?): Result<Record> {
//        return dslContext.fetch(sql)
//    }
//
//    /**
//     * Fetch result.
//     *
//     * @param sql      the SQL
//     * @param bindings the bindings
//     * @return the result
//     */
//    @NonNull
//    fun fetch(@NonNull sql: String?, vararg bindings: @NonNull Any?): Result<Record> {
//        return dslContext.fetch(sql, bindings)
//    }
//
//    /**
//     * Fetch result.
//     *
//     * @param sql   the SQL
//     * @param parts the parts
//     * @return the result
//     */
//    @NonNull
//    fun fetch(@NonNull sql: String?, vararg parts: @NonNull QueryPart?): Result<Record> {
//        return dslContext.fetch(sql, parts)
//    }
//
//    /**
//     * Fetch one record.
//     *
//     * @param sql      the SQL
//     * @param bindings the bindings
//     * @return the record
//     */
//    fun fetchOne(@NonNull sql: String?, vararg bindings: @NonNull Any?): Record {
//        return dslContext.fetchOne(sql, bindings)
//    }
//
//    /**
//     * Fetch one record.
//     *
//     * @param sql   the SQL
//     * @param parts the parts
//     * @return the record
//     */
//    fun fetchOne(@NonNull sql: String?, vararg parts: @NonNull QueryPart?): Record {
//        return dslContext.fetchOne(sql, parts)
//    }
//
//    /**
//     * Execute int.
//     *
//     * @param sql      the SQL
//     * @param bindings the bindings
//     * @return the int
//     */
//    fun execute(@NonNull sql: String?, vararg bindings: @NonNull Any?): Int {
//        return dslContext.execute(sql, bindings)
//    }
//
//    /**
//     * Execute int.
//     *
//     * @param sql   the SQL
//     * @param parts the parts
//     * @return the int
//     */
//    fun execute(@NonNull sql: String?, vararg parts: @NonNull QueryPart?): Int {
//        return dslContext.execute(sql, parts)
//    }
//
//    /**
//     * Reload.
//     */
//    fun reload() {
//        this.closeConnection()
//        this.createConnection()
//    }
//
//    /**
//     * Close connection.
//     */
//    fun closeConnection() {
//        if (this.dataSource != null) {
//            dataSource.close()
//        }
//    }
//}
//
