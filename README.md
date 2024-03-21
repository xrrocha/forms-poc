# Forms POC

This project explores the implementation of a forms generator that, given the
metadata associated with a form, dynamically runs the form both as a Swing
application and as a web page.

This POC explores the following subjects:

- A general-purpose data definition DSL
- A _platform-independent_ GUI definition DSL
- Platform-dependent form interpreters (Swing, web)
- A platform-independent, _translatable_ expression language (Jexl, Javascript)

The data definition DSL should be capable of expressing arbitrarily complex
data structs made up of scalars, lists, sets and maps. This should make it
possible to pass forms rich seed object graphs (rather than, for instance,
only flat, tabular data.)

The GUI definition language should be declarative and expressive enough that
GUI descriptions can be _losslessly_ translated to a variety of target GUI
platforms such as web, Android (e.g. Jetpack Compose), iOS, etc.

The platform-dependent form interpreters should efficiently enact GUI
descriptions in platform-specific ways by leveraging each platform's native
capabilities and look-and-feel.

Finally, the type-safe, platform-independent expression language should 
translate expressions to each target platform such that, for instance,
any suitable JVM expression language (Jexl, JSR-223, etc.) can be used
for Swing, while Javascript can be generated for web. This is useful to,
for example, conditionally enabling/disabling form fields or sub- 
based on potentially complex runtime conditions.