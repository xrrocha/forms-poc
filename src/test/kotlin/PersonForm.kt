import java.awt.GridLayout
import javax.swing.*

fun main() {
    SwingUtilities.invokeLater {
        createAndShowForm()
    }
}

private fun createAndShowForm() {
    val frame = JFrame("Forms à la Carte").apply {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        contentPane = JPanel().apply {
            layout = GridLayout(0, 2)
            add(createFieldset("Person Form") {
                val panel = this
                add(JLabel("Id"))
                add(JTextField("12345678").apply { toolTipText = "8 digits" })
                add(JLabel("Name"))
                add(JTextField("John Doe").apply { toolTipText = "An alphabetic, space-separated person name" })
                add(JLabel("Gender"))
                add(JTextField("m").apply { toolTipText = "" })
                add(JLabel("Military Id"))
                add(JTextField("7654321").apply { toolTipText = "7 digits" })

                // Submit Button
                add(JLabel()) // Empty label for spacing
                add(JButton("Submit").apply {
                    addActionListener {
                        collectAndPrintData(panel)
                    }
                })
            })
        }
        pack()
        setLocationRelativeTo(null)
        isVisible = true
    }
}

private fun createFieldset(legend: String, block: JPanel.() -> Unit): JPanel =
    JPanel().apply {
        layout = GridLayout(0, 2)
        border = BorderFactory.createTitledBorder(legend)
        block()
    }

private fun collectAndPrintData(panel: JPanel) {
    for (i in 0 until (panel.componentCount - 2) step 2) {
        val label = panel.getComponent(i) as JLabel
        val field = panel.getComponent(i + 1) as JTextField
        println("${label.text}: ${field.text}")
    }
}
