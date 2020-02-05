# moxy-dagger-mvp
the application intended for training moxy and di applying in mvp-project

In this simple app we can see an implementation of the following tools:
<ul>
<li>
<h3>MVP</h3>
An architecture pattern providing code-modularity
</li>
<li>
<h3>Moxy</h3>
Tool which hepls you use view state for considering different events with ui (e.g. screen rotation).
It saves presenter independly, whether any view is bound to it or not. And, furthermore, presenter interacts with view through view-interface. 
You should do the following steps:
<ul>
<li>Implement required libraries (app/build.gradle):<br/>
<code>implementation 'com.arello-mobile:moxy:1.5.5'</code><br/>
<code>implementation 'com.arello-mobile:moxy-app-compat:1.5.5'</code><br/>
<code>kapt 'com.arello-mobile:moxy-compiler:1.5.5'</code>
</li>
<li>Create View-interface implementing <code>MvpView</code> from moxy-library</li>
<li>Create presenter-class extending <code>MvpPresenter<?(here should be your view-interface) extends MvpView></code></li>
<li>Inject to presenter viewState using <code>@InjectViewState</code> over the presenter-class</li>
<li>Implement view-interface at some activity, fragment or other</li>
<li>Inject presenter into view through <code>@InjectPresenter</code> annotation over required presenter-field in view-class</li>
<li>That's all) Now you can use presenter without caring about his recreation and configuration changes</li>
</ul>
</li>
<li>
<h3>Dagger 2</h3>
Dependency injection applying
</li>
</ul>
