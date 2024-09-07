//package kz.hxncus.mc.minesonapikotlin.database
//
//import lombok.AllArgsConstructor
//import lombok.Builder
//import lombok.Data
//import lombok.NoArgsConstructor
//import org.bukkit.configuration.ConfigurationSection
//import org.jooq.SQLDialect
//import java.util.function.Function
//import java.util.stream.Collectors
//
///**
// * The type Database settings.
// */
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//class DatabaseSettings {
//    private val host: String? = null
//    private val port: String? = null
//    private val database: String? = null
//    private val username: String? = null
//    private val password: String? = null
//    private val tablePrefix: String? = null
//    private val sqlDialect: SQLDialect? = null
//    private val properties: Map<String, String>? = null
//
//    companion object {
//        /**
//         * From config database settings.
//         *
//         * @param section the section
//         * @return the database settings
//         */
//        fun fromConfig(section: ConfigurationSection): DatabaseSettings {
//            val dbSection = section.getConfigurationSection("database.sql") ?: return builder().build()
//            val sectionProperties = if (dbSection.contains("properties")) {
//                dbSection.getConfigurationSection("properties")
//            } else {
//                dbSection.createSection("properties")
//            }
//            return builder()
//                .host(dbSection.getString("host", "localhost"))
//                .port(dbSection.getString("port", "3306"))
//                .database(dbSection.getString("database", ""))
//                .username(dbSection.getString("username", "root"))
//                .password(dbSection.getString("password", ""))
//                .tablePrefix(dbSection.getString("table-prefix", ""))
//                .sqlDialect(
//                    SQLDialect.valueOf(
//                        section.getString("database.type", "SQLITE")
//                            .uppercase()
//                    )
//                ).properties(
//                    sectionProperties!!.getValues(false)
//                        .entries
//                        .stream()
//                        .collect(
//                            Collectors.toMap(
//                                Function<T, K> { java.util.Map.Entry.key },
//                                Function<T, U> { value: T ->
//                                    value.getValue()
//                                        .toString()
//                                }
//                            ))
//                ).build()
//        }
//    }
//}
