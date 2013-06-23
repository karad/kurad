# {{{{ Kurad

Admin tool for Play2 application

## Kuradの概要

<img src="https://raw.github.com/karad/kurad/master/public/logo.png" alt="kurad logo" />

Play 2.0.4 Java、Play 2.1.1 Java用のCRUDをベースとした管理画面ツールです。
簡単なAdmin画面を作成出来、モデル単位のテンプレートおよびロジックのカスタマイズが容易である点に優れています。

新規にアプリケーションを作成する際や、既に存在しているアプリケーションにAdmin画面を組み込む場合を想定しています。
アプリケーションへの実装が日々反映されていくような状況で、本体のコードと分離された形で管理画面の実装を進めていきたい場合に重宝します。
デフォルトのUIフレームワークとしてTwitterBootstrapをサポートしています。

<img src="https://raw.github.com/karad/kurad/master/public/capt_top.png" alt="" />

## Release note

* 2013/06/23
    * version 0.1.2 released

* 2013/05/1
    * version 0.1.1 pre released (private)

* 2013/01/27
    * version 0.1.0 pre released (private)

## インストール

Kuradは、Play 2.0.4 Java、Play 2.1.1 Javaに対応しています。
Scala版は将来的に対応するかもしれませんし、しないかもしれません。要望次第です。

### Play 2.0.4およびPlay 2.1.1

plugins.sbtを下記のようにし、Playを起動します。

```
addSbtPlugin("jp.greative" %% "kurad" % "0.1.2")

resolvers += "Greative Repository" at "https://github.com/karad/maven-repo/raw/master/release/"
```

Build.scalaに下記の依存関係を書きます。
TwitterBootstrapを使っているためです。

```
  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean
    ,"com.github.twitter" % "bootstrap" % "2.0.2"
  )
```

```
  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
    resolvers += "webjars" at "http://webjars.github.com/m2"
  )
```

## 使い方

### STEP 1

まず、モデルを用意します。
今回はmyAppという名前のアプリケーションにあるContactというモデルの管理画面を作ってみます。

Contactには、Name、E-mail、Content、TEL、created、modifiedがフィールドとしてあります。

よくあるお問い合わせフォームの内容です。
Contactは下記のようなコードです。既にEbeanの設定などは済んでいることとします。
済んでいない場合は、下記のような最低限の設定を行ってください。

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

### STEP 2

Adminツールには、管理用のアカウントが必要です。
管理画面用のアカウントは、Adminというモデルを作成する必要があります。

Kuradには、admin用モデルを生成するツールが内包されています。
Kuradのインストールを終えたら、下記のコマンドをPlayのコンソールでうちます。

```
[myApp] $ kurad admin
```

すると下記のような実行結果が表示されます。
デフォルトでは、app/models以下に生成されます。

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

```
[myApp] $ kurad template
```

すると下記のような結果が出て、ファイルが生成されます。
kurad/templates以下に生成されます。

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

### STEP 3

続いて、Kuradからモデルにアクセスできるように、publish-localコマンドを実行します。

```
[myApp] $ publish-local
```

すると下記のように出力されます（抜粋）

```
[myApp] $ publish-local
....
[info]  published myapp_2.10 to /YOUR_PLAY_HOME/repository/local/myapp/myapp_2.10/1.0-SNAPSHOT/jars/myapp_2.10.jar
                  ^^^^^^^^^^^^^^^
....
[success] Total time: 9 s, completed 2013/06/23 10:08:28
```

上記の出力結果のうち、今回のmyAppというアプリケーション名であれば、myapp_2.10がモジュールIDになります。また、myappがorganizationという形になります。アプリケーション名であるmyAppと違う点に注意してください。

publish-localしたjarをplugins.sbtにプロジェクト自体の読み込めるように設定します。

#### Play 2.0.4の場合

今回なら、

```
libraryDependencies += "myapp" % "myapp_2.9.1" % "1.0-SNAPSHOT"
```

になります。myappのところはアプリケーションにより異なります。また、アプリケーションのバージョン番号を指定している場合は1.0-SNAPSHOTのところを変更してください。

#### Play 2.1.1の場合

今回なら、

```
libraryDependencies += "myapp" % "myapp_2.10" % "1.0-SNAPSHOT"
```

になります。myappのところはアプリケーションにより異なります。
1.0-SNAPSHOTのところにはプロジェクトのバージョン番号が入ります。

### STEP 4

一度コンソールを終了し、
再びplayコンソールに入ります。
プラグインの依存関係を再読み込みするためです。

続いて、kuradコマンドを実行します。
コマンドの実行形式は以下です。

```
[myApp] kurad all MODEL_NAME
```

今回なら

```
[myApp] kurad all Contact
```

となります

### STEP 5

いよいよ、Ebeanモデル用の管理画面を出力していきます。
ちなみに上記の設定は一度行えば次から行う必要はありません。

まず、Adminモデルの管理画面を出力してみましょう。

```
[myApp] $ kurad all Admin
```

すると、下記のようにソースコードが生成された旨が表示されます。

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

### STEP 6

続いてContactモデルの生成も行いましょう。

```
[myApp] $ kurad all Contact
```

と打ちます。すると下記のようにファイルが生成または上書きされます。

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

### STEP 7

いよいよ管理画面にアクセスしてみます。runコマンドを実行してアプリケーションを立ち上げてください。

```
[myApp] $ run
```

デフォルトでは、管理画面のルーティングは、URL/crudに設定されています。

```
http://localhost:9000/crud
```

にアクセスしてみましょう。
すると下記の画面が表示されます。

<img src="https://raw.github.com/karad/kurad/master/public/capt_login.png" alt="" />

管理ユーザはまだいないのでログインができません。そこでログインユーザを作成します。
app/controllers/crud/AdminCrudController.javaの61行目付近の@play.mvc.Security.Authenticatedアノテーションをコメントアウトします。

```
    //@play.mvc.Security.Authenticated(models.crud.Secured.class)
    public static Result create() {
      if(request().method().equals("POST")) {
          Form<Admin> bindForm = form(Admin.class).bindFromRequest();
          if (bindForm.hasErrors()) {
          ....
    }
```

するとユーザ作成画面に入ることが出来ます。Playをrun下記URLにアクセスしてみてください。

```
http://localhost:9000/crud/admin/create
```

<img src="https://raw.github.com/karad/kurad/master/public/capt_admin.png" alt="" />

さあ、後は自由に管理画面を使ってPlayアプリを構築していきましょう。

## カスタマイズ

templateコマンドを実行すると、kuradで使われている様々なテンプレートファイルが生成されます。
結果として、kurad/templates以下にテンプレートおよび設定ファイルが出来ます。

```
[myApp] kurad template
```

kurad/templates/confディレクトリにkurad.confがあり、これをカスタマイズすることでモデルのディレクトリの変更や各種生成ルールを変更することが出来ます。

### テンプレートの編集

APP_ROUTE/kurad/templates/に格納されています。
テンプレートエンジンにはmustacheを使っており、拡張子は.mustacheです。

### テンプレートタグ

コンソールで設定した内容およびkurad.confの内容が出力可能です。
このあたり、いずれリファレンスマニュアルを作成します。

## ロードマップ

* version 0.1.3
    * csrfフィルタの導入
    * Adminモデルのカスタマイズ

## Licence

This software is licensed under the Apache 2 license, quoted below.

Copyright 2013 Greative (http://greative.jp).

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this project except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0.

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.



