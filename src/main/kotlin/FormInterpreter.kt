import kotlinx.html.*
import kotlinx.html.stream.createHTML
import java.awt.Desktop
import java.awt.GridLayout
import java.io.File
import javax.swing.*

interface FormInterpreter {
    fun interpret(form: Form)
}

object SwingFormInterpreter : FormInterpreter {
    override fun interpret(form: Form) {
        SwingUtilities.invokeLater {
            createAndShowForm(form)
        }
    }

    private fun createAndShowForm(form: Form) =
        JFrame(form.title).apply {
            defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
            contentPane = JPanel().apply {
                layout = GridLayout(0, 2)
                add(createFieldset(title) {
                    form.fields.toList()
                        .sortedBy { (_, field) -> field.position }
                        .forEach { (_, field) ->
                            println("Adding field ${field.label}")
                            add(JLabel(field.label))
                            add(JTextField().apply {
                                field.fyi?.also {
                                    toolTipText = it
                                }
                            })
                        }
                })
            }

            pack()
            setLocationRelativeTo(null)
            isVisible = true
        }

    private fun createFieldset(legend: String, block: JPanel.() -> Unit): JPanel =
        JPanel().apply {
            layout = GridLayout(0, 2)
            border = BorderFactory.createTitledBorder(legend)
            block()
        }
}

object WebFormInterpreter : FormInterpreter {
    override fun interpret(form: Form) {
        createHTML().html {
            head {
                title(form.title)
                meta {
                    charset = "UTF-8"
                }
                style {
                    unsafe {
                        +"""
                        fieldset{
                            display: inline-block;
                        }
                        label {
                            display:inline-block;
                            width:150px;
                        }
                    """.trimIndent()
                    }
                }
            }
            body {
                h1 { +"Forms à la Carte" }
                fieldSet {
                    legend { +" ${form.title} " }
                    form.fields.toList()
                        .sortedBy { (_, field) -> field.position }
                        .forEach { (fieldName, field) ->
                            div {
                                label {
                                    htmlFor = fieldName
                                    +field.label
                                }
                                input {
                                    id = fieldName
                                    name = fieldName
                                    field.fyi?.let {
                                        title = it
                                    }
                                }
                            }
                        }
                }
            }
        }
            .let { html ->
                File.createTempFile("forms", ".html")
                    .also {
                        // it.deleteOnExit()
                        it.printWriter().use { out ->
                            out.println(html)
                        }
                    }
                    .toURI()
                    .also { uri ->
                        Desktop.getDesktop().browse(uri)
                    }
            }
    }
}