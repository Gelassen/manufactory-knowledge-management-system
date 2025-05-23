package io.github.gelassen.manufactory_knowledge_management.model.request

class Test {
    fun test() {
        val dataset: MutableList<String?> = ArrayList<String?>()

        for (idx in dataset.indices) {
            var item = dataset.get(idx)
            item += "MQ"
            dataset.set(idx, item)
        }
    }
}
