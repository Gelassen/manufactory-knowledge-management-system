package io.github.gelassen.manufactory_knowledge_management.storage.model

class Schema {

    object Photofixation {
        const val TABLE_NAME = "Photofixation"
        const val UID = "uidPhoto"
        const val PHOTO = "photo"
        const val BREAKDOWN_ID = "breakdownId"
    }

    object Breakdown {
        const val TABLE_NAME = "Breakdown"
        const val UID = "uidBreakdown"
        const val FAILURE = "failure"
        const val SOLUTION = "solution"
        const val DATE = "date"
        const val MACHINE_ID = "machineId"
        /**
         * until schema is small try to avoid aliases in queries and keep all fields unique
         * */
        /*const val BREAKDOWN_TABLE_ALIAS = "breakdownTableAliasName"*/
    }

    object Machine {
        const val TABLE_NAME = "Machine"
        const val UID = "uidMachine"
        const val NAME = "name"
        const val MANUFACTURER = "manufacturer"
        const val BARCODE = "barcode"
    }
}