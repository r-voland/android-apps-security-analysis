import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    var apkToolInPattern = "renameManifestPackage: false"
    var apkToolOutPattern = "renameManifestPackage: "
    var workdirPath = ""
    var originalDirPath = ""
    var inPattern = ""
    var outPattern = ""
    var inSlashPAttern = ""
    var outSlashPAttern = ""
    var outputDirPath = ""
    var currentPath = ""
    val dirIndex: ArrayList<String> = arrayListOf()

    if (args.size != 4) {
        println("Arguments count error!")
        exitProcess(1)
    }
    originalDirPath = args[0]
    originalDirPath.trimEnd('/')
    inPattern = args[1]
    outPattern = args[2]
    workdirPath = args[3]

    inSlashPAttern = inPattern.replace('.', '/')
    outSlashPAttern = outPattern.replace('.', '/')
    outputDirPath = "$workdirPath/$outPattern"
    currentPath = originalDirPath

    if (!File(originalDirPath).isDirectory) {
        println("No Original Directory  $originalDirPath was found or Path Error.")
        exitProcess(1)
    }

    if (!File(workdirPath).isDirectory) {
        println("No directory  $workdirPath was found. Creating One New...")
        if (!File(workdirPath).mkdir()) {
            println("Creating Work Directory Error!")
            exitProcess(1)
        }
    }
    println("Creating Output Directories...")
    if (!File(outputDirPath).mkdir()) {
        println("Creating Output Directory Error! Maybe already Has - Delete first")
        exitProcess(1)
    }

    println("Step 1. Renaming Directories...")

    try {

        File(originalDirPath).walkTopDown().forEach { file ->

            if (file.isDirectory) {
                dirIndex.add(file.path)
            }
        }
        val tmpFile = File(originalDirPath)
        val tmpDir = tmpFile.parent

        val tmpArray: ArrayList<String> = arrayListOf()
        dirIndex.forEach {
            val tmpPath = it.replace(tmpDir, "")
            var workPath = "$outputDirPath$tmpPath"
            workPath = workPath.replace(inSlashPAttern, outSlashPAttern)
            tmpArray.add(workPath)
        }
        tmpArray.forEach {
            Files.createDirectories(Paths.get(it))
        }

    } catch (e: java.lang.Exception) {
        println("Directory Operation Error. Operation not completed")
        e.printStackTrace()
        exitProcess(1)
    }

    println("Done.")

    println("Step 2. Working with Files...")
    try {
        File(originalDirPath).walkTopDown().forEach { file ->

            if (file.isFile) {

                val tmpFile = File(originalDirPath)
                val tmpDir = tmpFile.parent

                currentPath = file.path.replace(tmpDir, "")

                var workPath = "$outputDirPath$currentPath"

                workPath = workPath.replace(inSlashPAttern, outSlashPAttern)

                var tmpPath = ""

                val workFile = File(workPath)
                tmpPath = "${workFile.parent}/${file.name}"

                val newFile = File(tmpPath)

                Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING)

                if (file.name.contains(".smali") ||
                    file.name.contains(".xml") ||
                    file.name.contains(".yml")
                ) {
                    val bufferedReader = newFile.bufferedReader()
                    val text: List<String> = bufferedReader.readLines()
                    val outText: ArrayList<String> = arrayListOf()
                    for (line in text) {
                        if (file.name == "AndroidManifest.xml") {
                            outText.add(line.replace(inPattern, outPattern))
                        } else if (file.name == "apktool.yml") {
                            outText.add(line.replace(apkToolInPattern, "$apkToolOutPattern$outPattern"))
                        } else {
                            val tmpLine = line.replace(inSlashPAttern, outSlashPAttern)
                            val tmpLine1 = tmpLine.replace(inPattern, outPattern)
                            outText.add(tmpLine1)
                        }
                    }

                    newFile.bufferedWriter().use { out ->
                        outText.forEach {
                            out.write(it)
                            out.newLine()
                        }
                    }
                }
            }
        }
        println("Done.")
    } catch (e: java.lang.Exception) {
        println("File Operation Error. Operation not completed")
        e.printStackTrace()
        exitProcess(1)
    }
}
