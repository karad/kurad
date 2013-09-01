<img src="https://raw.github.com/karad/kurad/master/public/logo.png" alt="kurad logo" />

version 0.1.4

Admin tool for Play2 application.(English page will be coming soon.)

Kuradは、Play2アプリケーションのための管理ツールです。

"Kurad" is a management tool for Play2 application.

## About Kurad

<strike>Play 2.0.4 Java、</strike>Play 2.1.3 Java用のCRUDをベースとした管理画面ツールです。

It is a management tool for CRUD of Play 2.1.3 Java and <strike>Play 2.0.4 Java</strike>.

簡単なAdmin画面を作成出来ます。
モデル単位のテンプレートのカスタマイズが容易である点が優れています。

You can create a simple Admin screen, 
Template customization of the model unit is easy.

kuradは、新規作成、開発中の両方で、アプリケーションの管理ツールを生成できます。
本体のコードと分離された形で管理画面の実装を進めていきたい場合に重宝します。

In both New, in development, kurad can generate a management tool for the application.

kurad will come in handy if you want to do the implementation of management tools by separating the code of the application itself.

デフォルトのUIフレームワークとしてTwitterBootstrapをサポートしています。

I support TwitterBootstrap as the UI framework of default.

<img src="https://raw.github.com/karad/kurad/master/public/capt_top.png" alt="" />

## Release note

* 2013/08/31
    * version 0.1.4 released
        * Support of CSRF filter
        * Can edit Admin model id property name in kurad.conf
        * Fix some bugs...
        * Not support Play 2.0.4 Java
* 2013/07/10
    * version 0.1.3.1 released
* 2013/06/23
    * version 0.1.3 released
* 2013/06/21
    * version 0.1.2 released (private)
* 2013/01/27
    * version 0.1.0 pre released (private)

## Install
-------------------------------

Kuradは、<strike>Play 2.0.4 Java、</strike>Play 2.1.3 Javaに対応しています。
Scala版は将来的に対応するかもしれませんし、しないかもしれません。要望次第です。

Kurad is compatible with <strike>Play 2.0.4 Java, to </strike>2.1.3 Java Play.
Scala version might respond in the future, and it may not. Demand is up to you.

### <strike>Play 2.0.4 and </strike>Play 2.1.3

plugins.sbtを下記のようにし、Playを起動します。

Try to keep the following plugins.sbt, and then start the Play.

```
addSbtPlugin("jp.greative" %% "kurad" % "0.1.3.1")

resolvers += "Greative Repository" at "https://github.com/karad/maven-repo/raw/master/release/"
```

Build.scalaに下記の依存関係を書きます。
kuradがTwitterBootstrapを使っているためです。また、CSRFフィルターを使うためのfiltersも依存関係に記述します。

I write the following dependencies to Build.scala.
This is because kurad is using TwitterBootstrap.

#### Example. Play 2.1.3

```
  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,
    filters,
    ,"com.github.twitter" % "bootstrap" % "2.0.2"
  )
```

```
  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
    resolvers += "webjars" at "http://webjars.github.com/m2"
  )
```

## Usage

### STEP 1

まず、モデルを用意します。
今回はmyAppという名前のアプリケーションにあるContactというモデルの管理画面を作ってみます。

First, I will provide a model.
I'll try to make the management of screen model called Contact in the application named myApp this time.

Contactには、Name、E-mail、Content、TEL、created、modifiedがフィールドとしてあります。

The Contact, there as a field Name, E-mail, Content, TEL, created, is modified.

よくあるお問い合わせフォームの内容です。
Contactは下記のようなコードです。

The contents of the inquiry form with a well.
Contact is the code as shown below.

```
package models;

import .....;

@Entity
@Table(name="contacts")
public class Contact extends Model {
    
    @Id
    public Long identifier;
    @Constraints.Required
    public String name;
    public String email;
    @Column(columnDefinition = "TEXT")
    public String content;
    public String tel;
    @Formats.DateTime(pattern="dd/MM/yyyy")
    public Date created;
    @Formats.DateTime(pattern="dd/MM/yyyy")
    public Date modified = new Date();

    .... getters and setters

}
```

既にEbeanの設定などは済んでいることとします。
済んでいない場合は、下記のような最低限の設定を行ってください。

I suppose that such as setting Ebean lives already.
If you have not already done so, please set the minimum as follows.

```
# Database configuration
# ~~~~~ 
# You can declare as many datasources as you want.
# By convention, the default datasource is named `default`
#
db.default.driver=org.h2.Driver
db.default.url="jdbc:h2:mem:play"
db.default.user=sa
db.default.password=""
#
# You can expose this datasource via JNDI if needed (Useful for JPA)
# db.default.jndiName=DefaultDS

# Evolutions
# ~~~~~
# You can disable evolutions if needed
# evolutionplugin=disabled

# Ebean configuration
# ~~~~~
# You can declare as many Ebean servers as you want.
# By convention, the default server is named `default`
#
ebean.default="models.*"
```

アプリケーション名は「myApp」としました。

I was made to "myApp" application name.

また、CSRFフィルターを有効にするためにapp/Global.java（ない場合は作成します）に下記のメソッドを追記します。Global.javaの作り方はPlay frameworkのマニュアルなどを参照ください。

```
    @Override
    public <T extends EssentialFilter> Class<T>[] filters() {
        return new Class[]{
                CSRFFilter.class
        };
    }
```

### STEP 2

Adminツールには、管理用のアカウントが必要です。
管理画面用のアカウントは、Adminというモデルを作成する必要があります。

The Admin tool, you must have an account for management.
The account management screen, you need to create a model called Admin.

Kuradには、admin用モデルを生成するツールが内包されています。
Kuradのインストールを終えたら、下記のコマンドをPlayのコンソールでうちます。

The Kurad, a tool that generates admin for model is enclosed.
When you have finished the installation of Kurad, Shoot in the console of the Play the following command.

```
[myApp] $ kurad admin
```

すると下記のような実行結果が表示されます。
デフォルトでは、app/models以下に生成されます。

Then run the following results will be displayed.
By default, this file is generated in the app / models below.

```
==============================================================

       {{{{ kurad - CRUD tool for web application 

==============================================================

[mode]   admin
--------------------------------------------------------------
create model files...

[kurad : created file] app/models/Admin.java
--------------------------------------------------------------
```

### STEP 3

続いて、設定用のコンフィグファイルを含んだテンプレート一覧をプロジェクト内に生成しておきます。
これらを変更すると、Admin画面をカスタマイズできます。
下記コマンドをうちます。

Then, you have generated in the project in the list of templates that contains the configuration files for configuration.
If you change them, you can customize the Admin screen.
I shoot the following command.

```
[myApp] $ kurad template
```

すると下記のような結果が出て、ファイルが生成されます。
kurad/templates以下に生成されます。

Then the following results came out, the file is generated.
I is generated kurad / templates below.

```
==============================================================

       {{{{ kurad - CRUD tool for web application 

==============================================================

[mode]   template
--------------------------------------------------------------
[kurad : created file] kurad/templates/conf/kurad.conf
[kurad : created file] kurad/templates/dateUtil.mustache
[kurad : created file] kurad/templates/defaultController.mustache
[kurad : created file] kurad/templates/defaultController_Admin.mustache
[kurad : created file] kurad/templates/defaultCreate.mustache
[kurad : created file] kurad/templates/defaultDelete.mustache
[kurad : created file] kurad/templates/defaultIndex.mustache
[kurad : created file] kurad/templates/defaultModel_Admin.mustache
[kurad : created file] kurad/templates/defaultModel.mustache
[kurad : created file] kurad/templates/defaultModelAdmin.mustache
[kurad : created file] kurad/templates/defaultSearch.mustache
[kurad : created file] kurad/templates/defaultTopController.mustache
[kurad : created file] kurad/templates/defaultUpdate.mustache
[kurad : created file] kurad/templates/defaultView.mustache
[kurad : created file] kurad/templates/defaultViewCreate.mustache
[kurad : created file] kurad/templates/defaultViewDetail.mustache
[kurad : created file] kurad/templates/defaultViewIndex.mustache
[kurad : created file] kurad/templates/defaultViewMain.mustache
[kurad : created file] kurad/templates/defaultViewTop.mustache
[kurad : created file] kurad/templates/defaultViewUpdate.mustache
[kurad : created file] kurad/templates/inputNormal.mustache
[kurad : created file] kurad/templates/inputTable.mustache
[kurad : created file] kurad/templates/inputTableWithManyToMany.mustache
[kurad : created file] kurad/templates/inputTableWithManyToOne.mustache
[kurad : created file] kurad/templates/inputTableWithOneToMany.mustache
[kurad : created file] kurad/templates/inputTableWithOneToOne.mustache
[kurad : created file] kurad/templates/listTable.mustache
[kurad : created file] kurad/templates/listTableManyToMany.mustache
[kurad : created file] kurad/templates/listTableManyToOne.mustache
[kurad : created file] kurad/templates/optionModel.mustache
[kurad : created file] kurad/templates/optionUtil.mustache
[kurad : created file] kurad/templates/paginate.mustache
[kurad : created file] kurad/templates/paginateSetting.mustache
[kurad : created file] kurad/templates/paging.mustache
[kurad : created file] kurad/templates/pagingBean.mustache
[kurad : created file] kurad/templates/routesTagReg.mustache
[kurad : created file] kurad/templates/routesTagScaffold.mustache
[kurad : created file] kurad/templates/routesTagTop.mustache
[kurad : created file] kurad/templates/searchEngine.mustache
[kurad : created file] kurad/templates/textareaTable.mustache
--------------------------------------------------------------
```

### STEP 4

続いて、Kuradからモデルにアクセスできるように、publish-localコマンドを実行します。

Then, you will have access to the model from Kurad, and then run the publish-local command.

```
[myApp] $ publish-local
```

すると下記のように出力されます（抜粋）

Then you will see output similar to the following (excerpt)

```
[myApp] $ publish-local
....
[info]  published myapp_2.10 to /YOUR_PLAY_HOME/repository/local/myapp/myapp_2.10/1.0-SNAPSHOT/jars/myapp_2.10.jar
                  ^^^^^^^^^^^^^^^
....
[success] Total time: 9 s, completed 2013/06/23 10:08:28
```

上記の出力結果のうち、今回のmyAppというアプリケーション名であれば、myapp_2.10がモジュールIDになります。また、myappがorganizationという形になります。アプリケーション名であるmyAppと違う点に注意してください。

Of the output result of the above, if the application named myApp this, myapp_2.10 is the module ID. In addition, myapp is the form of organization. Please note that different from the myApp is the application name.

publish-localしたjarをplugins.sbtにプロジェクト自体の読み込めるように設定します。

Set to read the project itself to plugins.sbt the jar that you publish-local.

#### Play 2.0.4の場合(not working)

#### For Play 2.0.4

今回なら、

If this time,

```
libraryDependencies += "myapp" % "myapp_2.9.1" % "1.0-SNAPSHOT"
```

上記のようになります。myappのところはアプリケーションにより異なります。また、アプリケーションのバージョン番号を指定している場合は1.0-SNAPSHOTのところを変更してください。

Is as above. At myapp depends on the application. In addition, please change the place of the 1.0-SNAPSHOT if you specify the version number of the application.

#### Play 2.1.3の場合

#### For Play 2.1.3

今回なら、

If this time,

```
libraryDependencies += "myapp" % "myapp_2.10" % "1.0-SNAPSHOT"
```

上記のようになります。myappのところはアプリケーションにより異なります。
1.0-SNAPSHOTのところにはプロジェクトのバージョン番号が入ります。

Is as above. At myapp depends on the application.
Version number of the project will contain at the 1.0-SNAPSHOT.

### STEP 5

一度コンソールを終了し、再びplayコンソールに入ります。
プラグインの依存関係を再読み込みするためです。

Exit the console once, to enter the play console again.
This is to reload the dependency of the plug-in.

続いて、kuradコマンドを実行します。
コマンドの実行形式は以下です。

Then, you can run the command kurad.
Execution form of the command is as follows.

```
[myApp] kurad all MODEL_NAME
```

今回なら

If this time,

```
[myApp] kurad all Contact
```

上記のようになります。

Is as above.

### STEP 6

いよいよ、Ebeanモデル用の管理画面を出力していきます。
ちなみに上記の設定は一度行えば次から行う必要はありません。

Finally, I will continue to output the management screen Ebean models.
By the way, you do not need to be performed from the following be performed once the above settings.

まず、Adminモデルの管理画面を出力してみましょう。

First, let's output the management of the Admin screen model.

```
[myApp] $ kurad all Admin
```

すると、下記のようにソースコードが生成された旨が表示されます。

Then, that the source code was generated as described below will be displayed.

```
==============================================================

       {{{{ kurad - CRUD tool for web application 

==============================================================

[mode]   all
[model]  Admin
--------------------------------------------------------------
writing all files...

[kurad : created file] app/controllers/crud/AdminCrudController.java
[kurad : created file] app/controllers/crud/TopCrudController.java
[kurad : created file] app/models/crud/AdminService.java
[kurad : created file] app/models/crud/PagingSetting.java
[kurad : created file] app/models/crud/OptionUtil.java
[kurad : created file] app/models/crud/Login.java
[kurad : created file] app/models/crud/SearchEngine.java
[kurad : created file] app/models/crud/Paging.java
[kurad : created file] app/models/crud/PagingBean.java
[kurad : created file] app/views/crud/paginate.scala.html
[kurad : created file] app/models/crud/DateUtil.java
[kurad : created file] app/models/crud/Secured.java
[kurad : created file] app/views/crud/Admin/index.scala.html
[kurad : created file] app/views/crud/Admin/create.scala.html
[kurad : created file] app/views/crud/Admin/update.scala.html
[kurad : created file] app/views/crud/Admin/detail.scala.html
[kurad : edited  file] conf/routes
[kurad : edited  file] conf/routes
[kurad : edited  file] conf/routes
[kurad : created file] app/views/crud/top_crud.scala.html
[kurad : created file] app/views/crud/main_crud.scala.html
[kurad : created file] app/views/crud/login_crud.scala.html
--------------------------------------------------------------
```

### STEP 7

続いてContactモデルの生成も行いましょう。

Let's also creates Contact model followed.

```
[myApp] $ kurad all Contact
```

上記のように打ちます。すると下記のようにファイルが生成または上書きされます。

I am out as described above. Then the file is overwritten or generated as described below.

```
==============================================================

       {{{{ kurad - CRUD tool for web application 

==============================================================

[mode]   all
[model]  Contact
--------------------------------------------------------------
writing all files...

[kurad : created file] app/controllers/crud/ContactCrudController.java
[kurad : created file] app/controllers/crud/TopCrudController.java
[kurad : created file] app/models/crud/ContactService.java
[kurad : created file] app/models/crud/PagingSetting.java
[kurad : created file] app/models/crud/OptionUtil.java
[kurad : created file] app/models/crud/Login.java
[kurad : created file] app/models/crud/SearchEngine.java
[kurad : created file] app/models/crud/Paging.java
[kurad : created file] app/models/crud/PagingBean.java
[kurad : created file] app/views/crud/paginate.scala.html
[kurad : created file] app/models/crud/DateUtil.java
[kurad : created file] app/models/crud/Secured.java
[kurad : created file] app/views/crud/Contact/index.scala.html
[kurad : created file] app/views/crud/Contact/create.scala.html
[kurad : created file] app/views/crud/Contact/update.scala.html
[kurad : created file] app/views/crud/Contact/detail.scala.html
[kurad : edited  file] conf/routes
[kurad : edited  file] conf/routes
[kurad : edited  file] conf/routes
[kurad : created file] app/views/crud/top_crud.scala.html
[kurad : created file] app/views/crud/main_crud.scala.html
[kurad : created file] app/views/crud/login_crud.scala.html
--------------------------------------------------------------
```

以上で、管理画面に必要なコードの生成は終了です。
必要であればモデルごとに更にkurad allコマンドを実行してください。

Above, generation of code required to manage screen is complete.
Please execute kurad all further commands for each model if necessary.

### STEP 8

いよいよ管理画面にアクセスしてみます。runコマンドを実行してアプリケーションを立ち上げてください。

I try to access the management screen finally. Please launch the application by running the run command.

```
[myApp] $ run
```

デフォルトでは、管理画面のルーティングは、URL/crudに設定されています。

By default, routing management screen, which is set to URL / crud.

```
http://localhost:9000/crud
```

にアクセスしてみましょう。
すると下記の画面が表示されます。

Let's have access to.
Then the following screen appears.

<img src="https://raw.github.com/karad/kurad/master/public/capt_login.png" alt="" />

管理ユーザはまだいないのでログインができません。そこでログインユーザを作成します。
app/controllers/crud/AdminCrudController.javaの61行目付近の@play.mvc.Security.Authenticatedアノテーションをコメントアウトします。

You can not log in because not yet user management. So I will create a login user.
I will comment out the line 61 near the app / controllers / crud / AdminCrudController.java the @ play.mvc.Security.Authenticated annotation.

```
    //@play.mvc.Security.Authenticated(models.crud.Secured.class)
    public static Result create() {
      if(request().method().equals("POST")) {
          Form<Admin> bindForm = form(Admin.class).bindFromRequest();
          if (bindForm.hasErrors()) {
          ....
    }
```

するとユーザ作成画面に入ることが出来ます。Playをrunして、下記URLにアクセスしてみてください。

Then you can enter the user-created screen. To run the Play, please try to access the following URL.

```
http://localhost:9000/crud/admin/create
```

<img src="https://raw.github.com/karad/kurad/master/public/capt_admin.png" alt="" />

さあ、後は自由に管理画面を使ってPlayアプリを構築していきましょう。

Come on, let's build the Play application using the control screen freely after.

## Customize

templateコマンドを実行すると、kuradで使われている様々なテンプレートファイルが生成されます。
結果として、kurad/templates以下にテンプレートおよび設定ファイルが出来ます。

When you run the command template, template various files that are used in kurad is generated.
As a result, configuration files and templates can be kurad / templates below.

```
[myApp] kurad template
```

kurad/templates/confディレクトリにkurad.confがあり、これをカスタマイズすることでモデルのディレクトリの変更や各種生成ルールを変更することが出来ます。

You can change a variety of generation rules to change directories and the model that there is a kurad.conf to kurad / templates / conf directory, to customize it.

### Kurad.conf

#### Admin Menu

デフォルトでは、Adminモデルに関するメニューが設定されています。

By default, the Admin menu on the model has been set.

```
kurad.setting.view.menus=["Admin"]
```

ここを変更するとメニューに追加されます。例えば、Adminモデルの他に、ContactモデルやAddressモデルを追加したい場合は下記のようにします。

It is added to the menu to change here. For example, do the following if in addition to the Admin model, you want to add the Address and Contact model model.

```
kurad.setting.view.menus=["Admin","Contact","Address"]
```

### Edit Template

APP_ROUTE/kurad/templates/に格納されています。
テンプレートエンジンにはmustacheを使っており、拡張子は.mustacheです。

It is located in the APP_ROUTE / kurad / templates /.
It uses the mustache in the template engine, extension is. Mustache.

### Template tag

コンソールで設定した内容およびkurad.confの内容が出力可能です。
このあたり、いずれリファレンスマニュアルを作成します。

The contents of kurad.conf and what you have set in the console can be output.
Around here, I will create a one reference manual.

## Roadmap

* version 0.1.4
    * csrfフィルタのサポート
    * Support of csrf filter
    * Adminモデルのカスタマイズ
    * Customizing the Admin model

## Licence

This software is licensed under the Apache 2 license, quoted below.

Copyright 2013 Greative (http://greative.jp).

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this project except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0.

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.



