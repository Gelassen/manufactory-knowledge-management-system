package io.github.gelassen.manufactory_knowledge_management

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import io.github.gelassen.manufactory_knowledge_management.model.Machine
import io.github.gelassen.manufactory_knowledge_management.model.fromDomain
import io.github.gelassen.manufactory_knowledge_management.storage.AppDatabase
import io.github.gelassen.manufactory_knowledge_management.storage.dao.MachinesDao
import io.github.gelassen.manufactory_knowledge_management.storage.repositories.MachinesRepository
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException


class MachinesDaoTest {
    private lateinit var machinesDao: MachinesDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        )
            .build()
        machinesDao = db.machinesDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @Throws(Exception::class)
    fun getMachineByBarcode_withValidBarcodeAndStorage_receivesValidResponse() = runTest {
        val barcodes = arrayOf("123bqwqasd", "098234jkfwje0")
        val machineRepository = MachinesRepository(machinesDao)
        val dataset = getStubMachines(*barcodes)
        launch { machinesDao.saveMachine(*dataset.map { it.fromDomain() }.toTypedArray()) }
        advanceUntilIdle()

        val result = machineRepository.getMachineByBarcode(barcodes[0])

        assertEquals(dataset[0].name, result!!.name)
        assertEquals(dataset[0].breakdowns, result.breakdowns)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @Throws(Exception::class)
    fun getMachineByBarcode_withNOTValidBarcodeAndStorage_receivesValidResponse() = runTest {
        val barcodes = arrayOf("123bqwqasd", "098234jkfwje0")
        val machineRepository = MachinesRepository(machinesDao)
        val dataset = getStubMachines(*barcodes)
        launch { machinesDao.saveMachine(*dataset.map { it.fromDomain() }.toTypedArray()) }
        advanceUntilIdle()

        val result = machineRepository.getMachineByBarcode("not-valid-barcode")

        assertNull(result)
//        assertEquals(dataset.get(0).name, result.name)
//        assertEquals(dataset.get(0).breakdowns, result.breakdowns)
    }

    companion object Utils {

        fun getStubMachines(vararg barcodes: String): MutableList<Machine> {
            return mutableListOf<Machine>(
                Machine(0L, "Sodick 300", "Sodick", barcodes[0]),
                Machine(0L, "Fanuc 2000", "Fanuc", barcodes[1])
            )
        }
    }

}