@Service
class QrService {

    fun generateQrValue(machineId: Long): String {
        return "https://your-domain.com/machines/$machineId"
    }
}