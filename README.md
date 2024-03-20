# Forms POC

This project explores the implementation of a forms generator that, given the
metadata associated with a form, dynamically runs the form both as a Swing
application and as a web page.

This POC explores the following subjects:

- A general-purpose data definition DSL
- A _platform-independent_ GUI definition DSL
- Platform-dependent form interpreters (Swing, web)
- A platform-independent, _translatable_ expression language (Jexl, Javascript)
