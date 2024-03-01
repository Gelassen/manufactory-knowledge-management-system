package io.github.gelassen.manufactory_knowledge_management.storage.model

class Schema {

    object Photofixation {
        const val TABLE_NAME = "Photofixation"
        const val UID = "uid"
        const val PHOTO = "photo"
        const val BREAKDOWN_ID = "breakdownId"
    }

    object Breakdown {
        const val TABLE_NAME = "Breakdown"
        const val UID = "uid"
        const val FAILURE = "failure"
        const val SOLUTION = "solution"
        const val DATE = "date"
        const val MACHINE_ID = "machineId"
    }

    object Machine {
        const val TABLE_NAME = "Machine"
        const val UID = "uid"
        const val NAME = "name"
        const val MANUFACTURER = "manufacturer"
        const val BARCODE = "barcode"
    }
}