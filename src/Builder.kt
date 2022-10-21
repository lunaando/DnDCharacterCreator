import java.util.*
import kotlin.collections.ArrayList

class Builder {

    companion object {

        private val scan = Scanner(System.`in`)

        fun selection(prompt: String, option: Array<String>, selection: Array<String>): String {
            var check = true
            var choice = 0
            while (check) {
                print(prompt)
                for (i in option.indices) {
                    print("${i + 1}. ${option[i]} ")
                }
                try {
                    choice = scan.nextInt() - 1
                    check = false
                } catch (e: Exception) {
                }
            }
            check = false

            try {
                return selection[choice]
            } catch (ie: ArrayIndexOutOfBoundsException) {
                while (!check) {
                    println("Improper selection.\nPress enter to try again.")
                    scan.nextLine()
                    return ""
                }
            }
            return selection[choice]
        }

        fun dataLine(prompt: String, names: ArrayList<String>, data: ArrayList<Int>) {
            while (names.size < data.size) {
                names.add("")
            }
            println(prompt)
            for (i in data.indices) {
                println(names[i] + " " + data[i] + " ")
            }
        }

        fun itemToList(prompt: String, itemToList: String, option: Array<String>, selection: Array<String>): String {
            var itemList = itemToList
            while (itemList == "") {
                itemList = selection(prompt, option, selection)
            }
            return itemList
        }
    }
}