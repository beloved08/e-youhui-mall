# node.js教程

## Node.js简介

Node.js 是一个基于 Chrome JavaScript 运行时建立的一个平台。

Node.js 是一个事件驱动 I/O 服务端 JavaScript 环境，基于 Google 的 V8 引擎，V8 引擎执行 Javascript 的速度非常快，性能非常好。

## Node.js安装配置

Node.js 安装包及源码下载地址为：https://nodejs.org/zh-cn/download/

## Node.js创建第一个应用

```javascript
// 引入http
var http = require("http");

// http.createServer() 方法创建服务器
// 并使用 listen 方法绑定 8888 端口。 函数通过 request, response 参数来接收和响应数据。
http.createServer(function(req, res){
    res.writeHead(200, {
        'Content-Type': 'text/plain'
    });

    res.end("Hello World");
}).listen(8888);

// 终端打印如下信息
console.log('Server running at http://127.0.0.1:8888/');
```

## Node.js REPL(交互式解释器)

Node.js REPL(Read Eval Print Loop:交互式解释器) 表示一个电脑的环境，类似 Windows 系统的终端或 Unix/Linux shell，我们可以在终端中输入命令，并接收系统的响应。

Node 自带了交互式解释器，可以执行以下任务：

* **读取** - 读取用户输入，解析输入的 Javascript 数据结构并存储在内存中。
* **执行** - 执行输入的数据结构
* **打印** - 输出结果
* **循环** - 循环操作以上步骤直到用户两次按下 **ctrl-c** 按钮退出。

Node 的交互式解释器可以很好的调试 Javascript 代码。

```javascript
$ node
> 
```

### REPL 命令

* **ctrl + c** - 退出当前终端。
* **ctrl + c 按下两次** - 退出 Node REPL。
* **ctrl + d** - 退出 Node REPL.
* **向上/向下 键** - 查看输入的历史命令
* **tab 键** - 列出当前命令
* **.help** - 列出使用命令
* **.break** - 退出多行表达式
* **.clear** - 退出多行表达式
* **.save \*filename\*** - 保存当前的 Node REPL 会话到指定文件
* **.load \*filename\*** - 载入当前 Node REPL 会话的文件内容。

## Node.js 回调函数

Node.js 异步编程的直接体现就是回调。

异步编程依托于回调来实现，但不能说使用了回调后程序就异步化了。

回调函数在完成任务后就会被调用，Node 使用了大量的回调函数，Node 所有 API 都支持回调函数。

例如，我们可以一边读取文件，一边执行其他命令，在文件读取完成后，我们将文件内容作为回调函数的参数返回。这样在执行代码时就没有阻塞或等待文件 I/O 操作。这就大大提高了 Node.js 的性能，可以处理大量的并发请求

### 阻塞代码实例

```javascript
// 阻塞代码实例
var fs = require("fs");

var data = fs.readFileSync('node.txt');

console.log(data.toString());
console.log("阻塞代码实例程序执行结束!");
```

### 非阻塞代码实例

```javascript
// 非阻塞代码实例
var fs = require('fs');
fs.readFile("node.txt",function(err, data){
    if(err) return console.error(err);

    console.log(data.toString());
});

console.log("非阻塞代码实例程序结束")
```

## Node.js 事件循环

Node.js 是单进程单线程应用程序，但是因为 V8 引擎提供的异步执行回调接口，通过这些接口可以处理大量的并发，所以性能非常高。

Node.js 几乎每一个 API 都是支持回调函数的。

Node.js 基本上所有的事件机制都是用设计模式中观察者模式实现。

Node.js 单线程类似进入一个while(true)的事件循环，直到没有事件观察者退出，每个异步事件都生成一个事件观察者，如果有事件发生就调用该回调函数

### 事件驱动程序

Node.js 使用事件驱动模型，当web server接收到请求，就把它关闭然后进行处理，然后去服务下一个web请求。

当这个请求完成，它被放回处理队列，当到达队列开头，这个结果被返回给用户。

这个模型非常高效可扩展性非常强，因为 webserver 一直接受请求而不等待任何读写操作。（这也称之为非阻塞式IO或者事件驱动IO）

在事件驱动模型中，会生成一个主循环来监听事件，当检测到事件时触发回调函数。

![image-20220719150033378](C:\Users\29787\AppData\Roaming\Typora\typora-user-images\image-20220719150033378.png)

```javascript

// 事件循环

// 引入events模块
var events = require("events");

// 创建 eventEmitter 对象
var eventEmitter = new events.EventEmitter();

// 创建事件处理程序
var connectHandler = function connected(){
    console.log("连接成功")

    // 触发 data_received 事件
    eventEmitter.emit("data_received");
}

// 绑定 connection 事件处理程序
eventEmitter.on("connection", function(){
    console.log("数据接收成功");
});

// 触发 connection 事件
eventEmitter.emit("connection");

console.log("程序执行完毕")

```



## Node.js EventEmitter

Node.js 所有的异步 I/O 操作在完成时都会发送一个事件到事件队列。

Node.js 里面的许多对象都会分发事件：一个 net.Server 对象会在每次有新连接时触发一个事件， 一个 fs.readStream 对象会在文件被打开的时候触发一个事件。 所有这些产生事件的对象都是 events.EventEmitter 的实例

### EventEmitter 类

events 模块只提供了一个对象： events.EventEmitter。EventEmitter 的核心就是事件触发与事件监听器功能的封装

EventEmitter 对象如果在实例化时发生错误，会触发 error 事件。当添加新的监听器时，newListener 事件会触发，当监听器被移除时，removeListener 事件被触发。

```javascript
//event.js 文件
var EventEmitter = require('events').EventEmitter; 
var event = new EventEmitter(); 
event.on('some_event', function() { 
    console.log('some_event 事件触发'); 
}); 
setTimeout(function() { 
    event.emit('some_event'); 
}, 1000); 
```

EventEmitter 的每个事件由一个事件名和若干个参数组成，事件名是一个字符串，通常表达一定的语义。对于每个事件，EventEmitter 支持 若干个事件监听器。

当事件触发时，注册到这个事件的事件监听器被依次调用，事件参数作为回调函数参数传递。

```javascript
// 引入 events 
var events = require("events");

// 创建 eventEmitter 对象
var eventEmitter = new events.EventEmitter();

// 绑定 someEvent 事件
eventEmitter.on("someEvent", function(s1,s2){
    console.log("listener1",s1,s2);
})

eventEmitter.on("someEvent", function(s1,s2){
    console.log("listener2",s1,s2);
})

// 触发 someEvent 事件
eventEmitter.emit("someEvent","参数1","参数2")
```

### EventEmitter方法

(1)、**addListener(event,listener)** :为指定事件添加一个监听器到监听器数组的尾部

(2)、**on(event,listener)** :为指定事件注册一个监听器，接受一个字符串 event 和一个回调函数

`server.on('connection',function(s1){`

`console.log(s1)`

`});`

(3)、**once(event,listener)** :为指定事件注册一个单次监听器，即监听器最多只会被触发一次，触发后like解除该监听器

`server.once(event,function(s1){`

`console.log(s1)`

`})`

(4)、**removeListener(event,listener)** :移除指定事件的某个监听器，监听器必须是该事件已经注册过的监听器；接收两个参数，第一个参数是事件名称，第二个参数是回调函数名称

`var callback = function(s1){`

`console.log(s1);`

`};`

`server.on('connection,callback')`

`// ....`

`server.removeListener('connection',callback);`

(5)、**removeAllListeners([event])** :移除所有事件的所有监听器，如果指定事件，则移除该指定事件的所有监听器

(6)、**setMaxListeners(n)** :，默认情况下，EventEmitters如果你添加的监听器超过10个就会输出警告信息，setMaxListeners函数用于修改监听器的默认限制数量

(7)、**listeners(event)** :返回指定事件的监听器数组

(8)、**emit(event,[arg1],[arg2],[...])** :按监听器的顺序执行每个监听器，如果事件有注册监听返回true，否则返回false

### 类方法

(1)、**listenerCount(emitter,event)** :返回指定事件的监听器数量

`events.emitter.listenerCount(eventName)`

### 事件

(1)、**newListener**

​	**event** - 字符串，事件名称

​	**listener** - 处理事件函数

该事件在添加新监听器时被触发

(2)、**removeListener**

​	**event** - 字符串，事件名称

​	**listener** - 处理事件函数

从指定监听器数组中删除一个监听器，需要注意的是，从操作将会改变处于被删监听器之后的那些监听器的索引

### error 事件

EventEmitter 定义了一个特殊的事件 error，它包含了错误的语义，我们在遇到 异常的时候通常会触发 error 事件。

当 error 被触发时，EventEmitter 规定如果没有响 应的监听器，Node.js 会把它当作异常，退出程序并输出错误信息。

我们一般要为会触发 error 事件的对象设置监听器，避免遇到错误后整个程序崩溃。例如：

```javascript
var events = require('events')
var emitter = new events.EventEmitter();
emitter.emit('error')
```

以上程序运行时将会报错

### 继承 EventEmitter

大多数时候我们不会直接使用 EventEmitter，而是在对象中继承它。包括 fs、net、 http 在内的，只要是支持事件响应的核心模块都是 EventEmitter 的子类。

为什么要这样做呢？原因有两点：

首先，具有某个实体功能的对象实现事件符合语义， 事件的监听和发生应该是一个对象的方法。

其次 JavaScript 的对象机制是基于原型的，支持 部分多重继承，继承 EventEmitter 不会打乱对象原有的继承关系。



## Node.js Buffer(缓冲区)

JavaScript 语言自身只有字符串数据类型，没有二进制数据类型。

但在处理像TCP流或文件流时，必须使用到二进制数据。因此在 Node.js中，定义了一个 Buffer 类，该类用来创建一个专门存放二进制数据的缓存区。

在 Node.js 中，Buffer 类是随 Node 内核一起发布的核心库。Buffer 库为 Node.js 带来了一种存储原始数据的方法，可以让 Node.js 处理二进制数据，每当需要在 Node.js 中处理I/O操作中移动的数据时，就有可能使用 Buffer 库。原始数据存储在 Buffer 类的实例中。一个 Buffer 类似于一个整数数组，但它对应于 V8 堆内存之外的一块原始内存。

`在v6.0之前创建Buffer对象直接使用new Buffer()构造函数来创建对象实例，但是Buffer对内存的权限操作相比很大，可以直接捕获一些敏感信息，所以在v6.0以后，官方文档里面建议使用* **Buffer.from()** *接口去创建Buffer对象。`

### Buffer 与字符编码

Buffer实例一般用于表示编码字符的序列，比如 UTF-8 、 UCS2 、 Base64 、或十六进制编码的数据。 通过使用显式的字符编码，就可以在 Buffer 实例与普通的 JavaScript 字符串之间进行相互转换。

```javascript
// Buffer
var buf = Buffer.from("test","ascii")

console.log(buf.toString('hex'))
console.log(buf.toString('base64'))
```

输出结果：

![image-20220719163151768](C:\Users\29787\AppData\Roaming\Typora\typora-user-images\image-20220719163151768.png)

**Node.js 目前支持的字符编码包括：**

* **ascii** - 仅支持 7 位 ASCII 数据。如果设置去掉高位的话，这种编码是非常快的。
* **utf8** - 多字节编码的 Unicode 字符。许多网页和其他文档格式都使用 UTF-8 。
* **utf16le** - 2 或 4 个字节，小字节序编码的 Unicode 字符。支持代理对（U+10000 至 U+10FFFF）。
* **ucs2** - **utf16le** 的别名。
* **base64** - Base64 编码。
* **latin1** - 一种把 **Buffer** 编码成一字节编码的字符串的方式。
* **binary** - **latin1** 的别名。
* **hex** - 将每个字节编码为两个十六进制字符。

### 创建 Buffer 类

Buffer 提供了以下 API 来创建 Buffer 类：

* **Buffer.alloc(size[,fill[,encoding]])**:返回一个指定大小的Buffer实例，如果没有设置fill，则默认填满0
* **Buffer.allocUnsafe(size)**:返回一个指定大小的Buffer实例，但它不会被初始化，所以它可能包含敏感的数据
* **Buffer.allocUnsafeSlow(size)**
* **Buffer.from(arrayBuffer[,byteOffset[,length]])**:返回一个新建的与给定的ArrayBuffer共享同一内存的Buffer
* **Buffer.from(buffer)**:复制传入的Buffer实例的数据，并返回一个新的Buffer实例
* **Buffer.from(string[,encoding])**:返回一个被string的值初始化的新的Buffer实例

### 写入缓冲区

#### 语法

```javascript
buf.write(string[,offset[,length]][,encoding])
```

#### 参数

参数描述如下：

* **string** - 写入缓冲区的字符串。
* **offset** - 缓冲区开始写入的索引值，默认为 0 。
* **length** - 写入的字节数，默认为 buffer.length
* **encoding** - 使用的编码。默认为 'utf8' 。

#### 返回值

返回实际写入的大小，如果buffer空间不足，则只会写入部分字符串

#### 实例

```javascript
var buf = Buffer.alloc(256);
var len = buf.write("sxgttythterg12345")
console.log(len)
```

### 从缓冲区读取数据

#### 语法

```javascript
buf.toString([encoding[,start[,end]]])
```

#### 参数

参数描述如下：

* **encoding** - 使用的编码。默认为 'utf8' 。
* **start** - 指定开始读取的索引位置，默认为 0。
* **end** - 结束位置，默认为缓冲区的末尾。

#### 返回值

解码缓冲区数据并使用指定的编码返回字符串。

#### 实例

```javascript
// 从缓冲区读取数据
var buf = Buffer.alloc(26);

for (var i = 0; i < 26; i++){
    buf[i] = i + 97;
}

console.log(buf.toString("ascii"))
console.log(buf.toString("ascii",0,5))
console.log(buf.toString("utf8",0,5))
console.log(buf.toString(undefined,0,5))
```

输出结果为：

![image-20220720093427067](C:\Users\29787\AppData\Roaming\Typora\typora-user-images\image-20220720093427067.png)

### 将 Buffer 转换为 JSON 对象

#### 语法

```javascript
buf.toJSON()
```

当字符串化一个 Buffer 实例时，[JSON.stringify()](https://www.runoob.com/js/javascript-json-stringify.html) 会隐式地调用该 **toJSON()**。

#### 返回值

返回JSON对象

#### 实例

```javascript
// 将 Buffer 转换为 JSON 对象
const buf = Buffer.from([0x1,0x2,0x3,0x4,0x5]);
const json = JSON.stringify(buf);

console.log(json)

const copy = JSON.parse(json,(key, value) => {
    return value && value.type === "Buffer" ? Buffer.from(value.data) : value;
})

console.log(copy);
```

### 缓冲区合并

#### 语法

```javascript
Buffer.concat(list[,totalLength])
```

#### 参数

参数描述如下：

* **list** - 用于合并的 Buffer 对象数组列表。
* **totalLength** - 指定合并后Buffer对象的总长度。

#### 返回值

返回一个多个成员合并的新 Buffer 对象。

#### 实例

```javascript
// 缓冲区合并
var buf1 = Buffer.from("node.js教程");
var buf2 = Buffer.from("www.baidu.com");
var buf3 = Buffer.concat([buf1,buf2]);

console.log(buf3.toString());
```

### 缓冲区比较

#### 语法

```javascript
buf.compare(otherBuffer)
```

#### 参数

参数描述如下：

* **otherBuffer** - 与 **buf** 对象比较的另外一个 Buffer 对象。

#### 返回值

返回一个数字，表示 **buf** 在 **otherBuffer** 之前，之后或相同。

#### 实例

```javascript
// 缓冲区比较
var buf1 = Buffer.from("ABCDE")
var buf2 = Buffer.from("ABCD")

var result = buf1.compare(buf2)

console.log(result);

if(result < 0){
    console.log(buf1 + " 在 " + buf2 + " 之前")
}else if(result == 0){
    console.log(buf1 + " 与 " + buf2 + " 相同")
}else{
    console.log(buf1 + " 在 " + buf2 + " 之后")
```

输出结果

![image-20220720095705877](C:\Users\29787\AppData\Roaming\Typora\typora-user-images\image-20220720095705877.png)

### 拷贝缓冲区

#### 语法

```javascript
buf.copy(targetBuffer[,targetStart[,sourceStart[,sourceEnd]]])
```

#### 参数

参数描述如下：

* **targetBuffer** - 要拷贝的 Buffer 对象。
* **targetStart** - 数字, 可选, 默认: 0
* **sourceStart** - 数字, 可选, 默认: 0
* **sourceEnd** - 数字, 可选, 默认: buffer.length

#### 返回值

没有返回值

#### 实例

```javascript
// 拷贝缓冲区
var buf1 = Buffer.from("asdfghjklwertyu")
var buf2 = Buffer.from("ASDREHT")

buf2.copy(buf1,2)

console.log(buf1.toString())
```

输出结果

![image-20220720100145265](C:\Users\29787\AppData\Roaming\Typora\typora-user-images\image-20220720100145265.png)

### 缓冲区裁剪

#### 语法

```javascript
buf.slice([start[,end]])
```

#### 参数

参数描述如下：

* **start** - 数字, 可选, 默认: 0
* **end** - 数字, 可选, 默认: buffer.length

#### 返回值

返回一个新的缓冲区，它和旧缓冲区指向同一块内存，但是从索引 start 到 end 的位置剪切。

#### 实例

```javascript
// 缓冲区裁剪
var buf1 = Buffer.from("bhcjfgwyeufwuef")
var buf2 = buf1.slice(0,5)
console.log(buf2.toString())
```

输出结果

![image-20220720100504597](C:\Users\29787\AppData\Roaming\Typora\typora-user-images\image-20220720100504597.png)

### 缓冲区长度

#### 语法

```javascript
buf.length
```

#### 返回值

返回 Buffer 对象所占据的内存长度

#### 实例

```javascript
// 缓冲区长度
var buf = Buffer.from("hcwjtfegfiu4t")

console.log(buf.length)
```

输出结果

![image-20220720100823330](C:\Users\29787\AppData\Roaming\Typora\typora-user-images\image-20220720100823330.png)



### 缓冲区方法参考手册

[Node.js Buffer(缓冲区) | 菜鸟教程 (runoob.com)](https://www.runoob.com/nodejs/nodejs-buffer.html)



## Node.js Stream(流)

Stream 是一个抽象接口，Node 中有很多对象实现了这个接口。例如，对http 服务器发起请求的request 对象就是一个 Stream，还有stdout（标准输出）。

Node.js，Stream 有四种流类型：

* **Readable** - 可读操作。
* **Writable** - 可写操作。
* **Duplex** - 可读可写操作.
* **Transform** - 操作被写入数据，然后读出结果。

所有的 Stream 对象都是 EventEmitter 的实例。常用的事件有：

* **data** - 当有数据可读时触发。
* **end** - 没有更多的数据可读时触发。
* **error** - 在接收和写入过程中发生错误时触发。
* **finish** - 所有数据已被写入到底层系统时触发。

### 从流中读取数据

```javascript
// 从流中读取数据
var fs = require("fs")
var data = ""

// 创建可读流
var readStream = fs.createReadStream('node.txt')
readStream.setEncoding("UTF8")

// 处理流事件

readStream.on("data", function(d){
    data += d
})

readStream.on("end", function() {
    console.log(data)
})

readStream.on("error", function(err) {
    console.log(err)
})
```

输出结果

![image-20220720102022244](C:\Users\29787\AppData\Roaming\Typora\typora-user-images\image-20220720102022244.png)

### 写入流

```javascript
// 写入流
var fs = require("fs")

var data = "看财经网很稳别扤";

// 创建一个可以写入的流，写入到文件 output.txt 中
var writeStream = fs.createWriteStream("output.txt")

// 设置编码
writeStream.write(data,"UTF8")

// 标记文件末尾
writeStream.end()

// 处理流事件
writeStream.on("finish",function(){
    console.log("写入完成")
})

writeStream.on("error",function(err){
    console.log(err)
})
```

输出结果

![image-20220720105322470](C:\Users\29787\AppData\Roaming\Typora\typora-user-images\image-20220720105322470.png)

output.txt

![image-20220720105345411](C:\Users\29787\AppData\Roaming\Typora\typora-user-images\image-20220720105345411.png)

### 管道流

管道提供了一个输出流到输入流的机制。通常我们用于从一个流中获取数据并将数据传递到另外一个流中。

```javascript
// 管道流
var fs = require("fs")

// 创建一个可读流
var readStream = fs.createReadStream("node.txt")

// 创建一个可写流
var writeStream = fs.createWriteStream("output.txt")

// 管道流读写操作
// 读取node.txt的内容，将其写入到output.txt中
readStream.pipe(writeStream)
```

### 链式流

链式是通过连接输出流到另外一个流并创建多个流操作链的机制。链式流一般用于管道操作

```javascript
// 链式流
// 压缩文件
var fs = require("fs")
var zlib = require("zlib")

// 压缩output.txt文件为output.txt.zip
fs.createReadStream("output.txt")
	.pipe(zlib.createGzip())
	.pipe(fs.createWriteStream("output.txt.zip"))
```

```javascript
// 链式流
// 解压文件
var fs = require("fs")
var zlib = require("zlib")

fs.createReadStream("output.txt.zip")
	.pipe(zlib.createGunzip())
	.pipe(fs.createWriteStream("output1.txt"))
```

## Node.js 模块系统

为了让Node.js的文件可以相互调用，Node.js提供了一个简单的模块系统。

模块是Node.js 应用程序的基本组成部分，文件和模块是一一对应的。换言之，一个 Node.js 文件就是一个模块，这个文件可能是JavaScript 代码、JSON 或者编译过的C/C++ 扩展

### 引入模块

main.js

```javascript
var hello = require("./hello")

hello.world("啦啦")
```

world.js

```javascript
exports.world = function(world) {
    console.log("hello " + world)
}
```

输出结果

![image-20220720111610405](C:\Users\29787\AppData\Roaming\Typora\typora-user-images\image-20220720111610405.png)

在以上示例中,hello.js 通过 **exports** 对象把 world 作为模块的访问接口，在 main.js 中通过 require('./hello') 加载这个模块，然后就可以直接访 问 hello.js 中 exports 对象的成员函数了。

有时候我们只是想把一个对象封装到模块中，格式如下：

```javascript
module.exports = function(){
	//....
}
```

world.js

```javascript
function Hello() {
    var name;
    this.setName = function(thyName) {
        name = thyName;
    }

    this.sayHello = function(){
        console.log("hello " + name);
    }
}

module.exports = Hello
```

main.js

```javascript
var Hello = require("./hello")

hello = new Hello()

hello.setName("李平")

hello.sayHello()
```

输出结果

![image-20220720112424966](C:\Users\29787\AppData\Roaming\Typora\typora-user-images\image-20220720112424966.png)

### require 方法中的文件查找策略

由于 Node.js 中存在 4 类模块（原生模块和3种文件模块），尽管 require 方法极其简单，但是内部的加载却是十分复杂的，其加载优先级也各自不同。如下图所示：

![image-20220720113047369](C:\Users\29787\AppData\Roaming\Typora\typora-user-images\image-20220720113047369.png)

### 从文件模块缓存中加载

尽管原生模块与文件模块的优先级不同，但是都会优先从文件模块的缓存中加载已经存在的模块

### 从原生模块加载

原生模块的优先级仅次于文件模块缓存的优先级。require 方法在解析文件名之后，优先检查模块是否在原生模块列表中。以http模块为例，尽管在目录下存在一个 http/http.js/http.node/http.json 文件，require("http") 都不会从这些文件中加载，而是从原生模块中加载。

原生模块也有一个缓存区，同样也是优先从缓存区加载。如果缓存区没有被加载过，则调用原生模块的加载方式进行加载和执行。

### 从文件加载

当文件模块缓存中不存在，而且不是原生模块的时候，Node.js 会解析 require 方法传入的参数，并从文件系统中加载实际的文件

require方法接受以下几种参数的传递：

* http、fs、path等，原生模块。
* ./mod或../mod，相对路径的文件模块。
* /pathtomodule/mod，绝对路径的文件模块。
* mod，非原生模块的文件模块。

### **exports 和 module.exports 的使用**

*如果要对外暴露属性或方法，就用 **exports** 就行，要暴露对象(类似class，包含了很多属性和方法)，就用 **module.exports**。*

**不建议同时使用 exports 和 module.exports。如果先使用 exports 对外暴露属性或方法，再使用 module.exports 暴露对象，会使得 exports 上暴露的属性或者方法失效。** **原因在于，exports 仅仅是 module.exports 的一个引用。在 nodejs 中，是这么设计 require 函数的：**

```javascript
function require(...){
  var module = {exports: {}};

  ((module, exports) => {
    function myfn () {}
    // 这个myfn就是我们自己的代码
    exports.myfn = myfn; // 这里是在原本的对象上添加了一个myfn方法。
    module.exports = myfn;// 这个直接把当初的对象进行覆盖。
  })(module,module.exports)
  return module.exports;
}
```

## Node.js 函数

在 JavaScript中，一个函数可以作为另一个函数的参数。我们可以先定义一个函数，然后传递，也可以在传递参数的地方直接定义函数。

Node.js 中函数的使用与 JavaScript 类似，举例来说，你可以这样做：

```javascript
function say(word) {
  console.log(word);
}

function execute(someFunction, value) {
  someFunction(value);
}

execute(say, "Hello");
```

###  匿名函数

我们可以把一个函数作为变量传递。但是我们不一定要绕这个"先定义，再传递"的圈子，我们可以直接在另一个函数的括号中定义和传递这个函数

```javascript
function execute(someFunction, value) {
  someFunction(value);
}

execute(function(word){ console.log(word) }, "Hello");
```

## Node.js 路由

我们要为路由提供请求的 URL 和其他需要的 GET 及 POST 参数，随后路由需要根据这些数据来执行相应的代码。

我们需要的所有数据都会包含在 request 对象中，该对象作为 onRequest() 回调函数的第一个参数传递。但是为了解析这些数据，我们需要额外的 Node.JS 模块，它们分别是 url 和 querystring 模块。

server.js

```javascript
var http = require('http');
var url = require('url');

function start(route){
    function onRequest(req, res) {
        console.log(req);
        var pathname = url.parse(req.url).pathname;
        console.log(pathname);

        route(pathname);

        res.writeHead(200,"Content-Type","text/plain");
        res.write("Hello world");
        res.end();

    }

    http.createServer(onRequest).listen(8888);
}

exports.start = start;
```

router.js

```javascript
function route(pathname) {
    console.log("About to route a request for " + pathname);
}

exports.route = route;
```

index.js

```javascript
var server = require('./server');
var router = require('./router');

server.start(router.route);
```

直接将事件名定义成路径

```javascript
let http = require("http");
let url = require("url");
const {exit} = require("process");
let events = require("events");

// 创建 EventEmitter 对象
let eventEmitter = new events.EventEmitter();

// on(event,listener):为指定事件注册一个监听器，
// 接受一个字符串 event 和一个回调函数
// route 根路径
eventEmitter.on("/", function(method,res){
    console.log("route 根路径")
    res.writeHead(200,{"Content-Type":"text/plain"});
    res.end("Hello World\n");
})

// route 404
eventEmitter.on("404", function(method,url,res){
    console.log("route 404")
    res.writeHead(404,{"Content-Type":"text/plain"});
    res.end("404 Not Found\n");
})

// 启动服务
http.createServer(function(req,res){
    console.log("请求路径： " + req.url);

    // 分发
    // listenerCount():返回指定事件的监听器数量
    if(eventEmitter.listenerCount(req.url) > 0){
        eventEmitter.emit(req.url,req.method,res)
    }else{
        eventEmitter.emit("404",req.method,req.url,res)
    }
}).listen(8888)
```

浏览器输入：http://localhost:8888/

输出结果

![image-20220720135042068](C:\Users\29787\AppData\Roaming\Typora\typora-user-images\image-20220720135042068.png)

![image-20220720135101171](C:\Users\29787\AppData\Roaming\Typora\typora-user-images\image-20220720135101171.png)

浏览器输入：http://localhost:8888/404

输出结果

![image-20220720135132631](C:\Users\29787\AppData\Roaming\Typora\typora-user-images\image-20220720135132631.png)

![image-20220720135207255](C:\Users\29787\AppData\Roaming\Typora\typora-user-images\image-20220720135207255.png)



## Node.js 全局对象

JavaScript 中有一个特殊的对象，称为全局对象（Global Object），它及其所有属性都可以在程序的任何地方访问，即全局变量。

在浏览器 JavaScript 中，通常 window 是全局对象， 而 Node.js 中的全局对象是 global，所有全局变量（除了 global 本身以外）都是 global 对象的属性。

在 Node.js 我们可以直接访问到 global 的属性，而不需要在应用中包含它。

### 全局对象与全局变量

**global** 最根本的作用是作为全局变量的宿主。按照 ECMAScript 的定义，满足以下条 件的变量是全局变量：

* 在最外层定义的变量；
* 全局对象的属性；
* 隐式定义的变量（未定义直接赋值的变量）。

当你定义一个全局变量时，这个变量同时也会成为全局对象的属性，反之亦然。需要注 意的是，在 Node.js 中你不可能在最外层定义变量，因为所有用户代码都是属于当前模块的， 而模块本身不是最外层上下文。

**注意：** 最好不要使用 var 定义变量以避免引入全局变量，因为全局变量会污染命名空间，提高代码的耦合风险。



### __filename

**__filename** 表示当前正在执行的脚本的文件名。它将输出文件所在位置的绝对路径，且和命令行参数所指定的文件名不一定相同。 如果在模块中，返回的值是模块文件的路径

#### 实例

```javascript
// 注意：__为两个下划线
console.log(__filename)
```

输出结果

![image-20220720140119239](C:\Users\29787\AppData\Roaming\Typora\typora-user-images\image-20220720140119239.png)

### __dirname

**__dirname** 表示当前执行脚本所在的目录。

#### 实例

```javascript
// 注意：__为两个下划线
console.log(__dirname)
```

输出结果

![image-20220720140440184](C:\Users\29787\AppData\Roaming\Typora\typora-user-images\image-20220720140440184.png)

### setTimeout(cb, ms)

**setTimeout(cb, ms)** 全局函数在指定的毫秒(ms)数后执行指定函数(cb)。：setTimeout() 只执行一次指定函数，返回一个代表定时器的句柄值。

#### 实例

```javascript
function printHello(){
    console.log("Hello World")
}
console.log("两秒后执行")

setTimeout(printHello,2000)
```

输出结果

![image-20220720140909306](C:\Users\29787\AppData\Roaming\Typora\typora-user-images\image-20220720140909306.png)

### clearTimeout(t)

**clearTimeout( t )** 全局函数用于停止一个之前通过 setTimeout() 创建的定时器。 参数 **t** 是通过 setTimeout() 函数创建的定时器。

#### 实例

```javascript

function printHello(){
    console.log("Hello World")
}
console.log("两秒后执行")

var t = setTimeout(printHello,2000)

// 清除定时器
clearTimeout(t);
console.log("清除定时器")
```

输出结果

![image-20220720141055571](C:\Users\29787\AppData\Roaming\Typora\typora-user-images\image-20220720141055571.png)

### setInterval(cb, ms)

**setInterval(cb, ms)** 全局函数在指定的毫秒(ms)数后执行指定函数(cb)。

返回一个代表定时器的句柄值。可以使用 **clearInterval(t)** 函数来清除定时器。

**setInterval()** 方法会不停地调用函数，直到 **clearInterval()** 被调用或窗口被关闭。

#### 实例

```javascript
function printHello(){
    console.log("Hello World")
}
console.log("两秒后执行")

setInterval(printHello,2000)
```

输出结果

![image-20220720141342907](C:\Users\29787\AppData\Roaming\Typora\typora-user-images\image-20220720141342907.png)

以上程序每隔两秒就会输出一次"Hello, World!"，且会永久执行下去，直到你按下 **ctrl + c** 按钮。

### process

process 是一个全局变量，即 global 对象的属性。它用于描述当前Node.js 进程状态的对象，提供了一个与操作系统的简单接口。

#### process事件

* **exit** - 当进程准备退出时触发。
* **beforeExit** - 当 node 清空事件循环，并且没有其他安排时触发这个事件。通常来说，当没有进程安排时 node 退出，但是 'beforeExit' 的监听器可以异步调用，这样 node 就会继续执行。
* **uncaughException** - 当一个异常冒泡回到事件循环，触发这个事件。如果给异常添加了监视器，默认的操作（打印堆栈跟踪信息并退出）就不会发生。
* **Signal** - 当进程接收到信号时就触发。信号列表详见标准的 POSIX 信号名，如 SIGINT、SIGUSR1 等。

#### 退出状态码

[Node.js 全局对象 | 菜鸟教程 (runoob.com)](https://www.runoob.com/nodejs/nodejs-global-object.html)

#### Process 属性

[Node.js 全局对象 | 菜鸟教程 (runoob.com)](https://www.runoob.com/nodejs/nodejs-global-object.html)

#### 方法参考手册

[Node.js 全局对象 | 菜鸟教程 (runoob.com)](https://www.runoob.com/nodejs/nodejs-global-object.html)



## Node.js 常用工具

util 是一个Node.js 核心模块，提供常用函数的集合，用于弥补核心 JavaScript 的功能 过于精简的不足。

使用方法

```javascript
const util = require("util")
```

### util.callbackify

**util.callbackify(original)** 将 `async` 异步函数（或者一个返回值为 `Promise` 的函数）转换成遵循异常优先的回调风格的函数，例如将 `(err, value) => ...` 回调作为最后一个参数。 在回调函数中，第一个参数为拒绝的原因（如果 `Promise` 解决，则为 `null`），第二个参数则是解决的值。

#### 实例

```javascript
// util
const util = require('util');

async function fn(){
    return "康师傅无人机";
}

const callbackFunction = util.callbackify(fn);

callbackFunction((err,ret) => {
    if (err) throw err;

    console.log(ret);
});
```

输出结果

![image-20220720142737637](C:\Users\29787\AppData\Roaming\Typora\typora-user-images\image-20220720142737637.png)

回调函数是异步执行的，并且有异常堆栈错误追踪。 如果回调函数抛出一个异常，进程会触发一个 '**uncaughtException**' 异常，如果没有被捕获，进程将会退出。

null 在回调函数中作为一个参数有其特殊的意义，如果回调函数的首个参数为 Promise 拒绝的原因且带有返回值，且值可以转换成布尔值 false，这个值会被封装在 Error 对象里，可以通过属性 reason 获取。

### util.inherits

**util.inherits(constructor, superConstructor)** 是一个实现对象间原型继承的函数。

JavaScript 的面向对象特性是基于原型的，与常见的基于类的不同。JavaScript 没有提供对象继承的语言级别特性，而是通过原型复制来实现的。

#### 实例

```javascript
var util = require('util');
function Base(){
    this.name = "Base";
    this.base = 1999;
    this.sayHello = function(){
        console.log("Hello " + this.name)
    };
}

Base.prototype.showName = function(){
    console.log(this.name)
}

function Sub(){
    this.name = "Sub";
}

util.inherits(Sub, Base);

var objBase = new Base();
objBase.showName();
objBase.sayHello();
console.log(objBase);

var objSub = new Sub();
objSub.showName();
// objSub.sayHello();
console.log(objSub);
```

定义了一个基础对象 Base 和一个继承自 Base 的 Sub，Base 有三个在构造函数内定义的属性和一个原型中定义的函数，通过util.inherits 实现继承。运行结果如下：

![image-20220720145059912](C:\Users\29787\AppData\Roaming\Typora\typora-user-images\image-20220720145059912.png)

**注意：**Sub 仅仅继承了Base 在原型中定义的函数，而构造函数内部创造的 base 属 性和 sayHello 函数都没有被 Sub 继承。

### util.inspect

**util.inspect(object,[showHidden],[depth],[colors])** 是一个将任意对象转换 为字符串的方法，通常用于调试和错误输出。它至少接受一个参数 object，即要转换的对象。

showHidden 是一个可选参数，如果值为 true，将会输出更多隐藏信息。

depth 表示最大递归的层数，如果对象很复杂，你可以指定层数以控制输出信息的多 少。如果不指定depth，默认会递归 2 层，指定为 null 表示将不限递归层数完整遍历对象。 如果 colors 值为 true，输出格式将会以 ANSI 颜色编码，通常用于在终端显示更漂亮 的效果。

特别要指出的是，util.inspect 并不会简单地直接把对象转换为字符串，即使该对 象定义了 toString 方法也不会调用。

#### 实例

```javascript
let util = require('util');
function Person(){
    this.name = "byVoid";
    this.toString = function(){
        return this.name;
    };
}

let obj = new Person();
console.log(util.inspect(obj));
console.log(util.inspect(obj,true))
```

输出结果

![65830267052](C:\Users\29787\AppData\Local\Temp\1658302670527.png)

### util.isArray(object)

如果给定的参数 "object" 是一个数组返回 true，否则返回 false

#### 实例

```javascript
let util = require('util');
console.log(util.isArray([]))
console.log(util.isArray(new Array))
console.log(util.isArray({}))
```

输出结果

![65830305551](C:\Users\29787\AppData\Local\Temp\1658303055518.png)

### util.isRegExp(object)

如果给定的参数 "object" 是一个正则表达式返回true，否则返回false

#### 实例

```javascript
let util = require('util');
console.log(util.isRegExp(/some regexp/))
console.log(util.isRegExp(new RegExp('another regexp')))
console.log(util.isRegExp({}))
```

输出结果

![65830331866](C:\Users\29787\AppData\Local\Temp\1658303318668.png)

### util.isDate(object)

如果给定的参数 "object" 是一个日期返回true，否则返回false。

#### 实例

```javascript
let util = require('util');

console.log(util.isDate(new Date))
console.log(util.isDate(Date()))
console.log(util.isDate({}))
```

输出结果

![65830367083](C:\Users\29787\AppData\Local\Temp\1658303670830.png)



## Node.js 文件系统

Node.js 提供一组类似 UNIX（POSIX）标准的文件操作API。 Node 导入文件系统模块(fs)语法如下所示：

`var fs = require("fs")`

### 异步和同步

Node.js 文件系统（fs 模块）模块中的方法均有异步和同步版本，例如读取文件内容的函数有异步的 fs.readFile() 和同步的 fs.readFileSync()。

异步的方法函数最后一个参数为回调函数，回调函数的第一个参数包含了错误信息(error)。

建议使用异步方法，比起同步，异步方法性能更高，速度更快，而且没有阻塞

```javaScript
var fs = require('fs');

// 异步读取
fs.readFile("node.txt",function(err, data){
    if (err) return console.error(err);

    console.log("异步读取文件信息");
    console.log(data.toString());
});

// 同步读取
var data = fs.readFileSync("node.txt")

console.log("同步读取文件信息")
console.log(data.toString());
```

输出结果

![65830513986](C:\Users\29787\AppData\Local\Temp\1658305139865.png)

### 打开文件

#### 语法

```javascript
fs.open(path,flags[,mode],callback)
```

#### 参数

参数使用说明如下：

* **path** - 文件的路径
* **flags** - 文件打开的行为。具体值详见下文
* **mode** - 设置文件模式(权限)，文件创建默认权限为 0666(可读，可写)
* **callback** - 回调函数，带有两个参数如：callback(err, fd)

##### lags 参数可以是以下值：

| **方式** |                          描述                          |
| :------: | :----------------------------------------------------: |
|    r     |      以读取模式打开文件。如果文件不存在抛出异常。      |
|    r+    |      以读写模式打开文件。如果文件不存在抛出异常。      |
|    rs    |                 以同步的方式读取文件。                 |
|   rs+    |              以同步的方式读取和写入文件。              |
|    w     |       以写入模式打开文件，如果文件不存在则创建。       |
|    wx    |    类似 'w'，但是如果文件路径存在，则文件写入失败。    |
|    w+    |       以读写模式打开文件，如果文件不存在则创建。       |
|   wx+    |   类似 'w+'， 但是如果文件路径存在，则文件读写失败。   |
|    a     |       以追加模式打开文件，如果文件不存在则创建。       |
|    ax    |   类似 'a'， 但是如果文件路径存在，则文件追加失败。    |
|    a+    |     以读取追加模式打开文件，如果文件不存在则创建。     |
|   ax+    | 类似 'a+'， 但是如果文件路径存在，则文件读取追加失败。 |

#### 实例

```javascript
var fs = require("fs");

// 异步打开文件
console.log("准备打开文件")
fs.open("node.txt","r+",function(err, data) {
    if (err) return console.error(err);

    console.log("文件打开成功");
});
```

输出结果

![65830583079](C:\Users\29787\AppData\Local\Temp\1658305830793.png)

### 获取文件信息

#### 语法

```javascript
fs.stat(path,callback)
```

#### 参数

参数使用说明如下：

* **path** - 文件路径
* **callback** - 回调函数，带有两个参数如：(err, stats), **stats** 是 fs.Stats 对象。

fs.stat(path)执行后，会将stats类的实例返回给其回调函数

##### stats类中的方法

|           方法            |                             描述                             |
| :-----------------------: | :----------------------------------------------------------: |
|      stats.isFile()       |            如果是文件返回 true，否则返回 false。             |
|    stats.isDirectory()    |            如果是目录返回 true，否则返回 false。             |
|   stats.isBlockDevice()   |           如果是块设备返回 true，否则返回 false。            |
| stats.isCharacterDevice() |          如果是字符设备返回 true，否则返回 false。           |
|  stats.isSymbolicLink()   |           如果是软链接返回 true，否则返回 false。            |
|      stats.isFIFO()       | 如果是FIFO，返回true，否则返回 false。FIFO是UNIX中的一种特殊类型的命令管道。 |
|     stats.isSocket()      |          如果是 Socket 返回 true，否则返回 false。           |

#### 实例

```javascript
var fs = require("fs");

console.log("准备打开文件")
fs.stat("node.txt",function(err, stat) {
    if (err) return console.error(err);

    console.log(stat)

    // 检测文件类型
    console.log("是否为文件？ " + stat.isFile())
    console.log("是否为目录？ " + stat.isDirectory())
});
```

输出结果

![65830659259](C:\Users\29787\AppData\Local\Temp\1658306592597.png)

### 写入文件

#### 语法

```javascript
fs.writeFile(file,data[,options],callback)
```

**fs.writeFile()** 直接打开文件默认是 **w** 模式，所以如果文件存在，该方法写入的内容会覆盖旧的文件内容。

#### 参数

参数使用说明如下：

* **file** - 文件名或文件描述符
* **data** - 要写入文件的数据，可以是 String(字符串) 或 Buffer(缓冲) 对象
* **options** - 该参数是一个对象，包含 {encoding, mode, flag}。默认编码为 utf8, 模式为 0666 ， flag 为 'w'
* **callback** - 回调函数，回调函数只包含错误信息参数(err)，在写入失败时返回。

#### 实例

```javascript
var fs = require('fs');

console.log("准备写入文件")
fs.writeFile("input.txt","那几款文化服务没",function(err) {
    if (err) return console.error(err);

    console.log("数据写入成功")
    console.log("-----------------------")
    console.log("读取写入的文件")
    fs.readFile("input.txt",function(err, data) {
        if (err) return console.error(err);

        console.log(data.toString())
    })
})
```

输出结果

![65830740166](C:\Users\29787\AppData\Local\Temp\1658307401668.png)

### 读取文件

#### 语法

```javascript
// 异步模式读取文件语法格式
fs.read(fd, buffer, offset, length, position, callback)
```

#### 参数

参数使用说明如下：

* **fd** - 通过 fs.open() 方法返回的文件描述符
* **buffer** - 数据写入的缓冲区
* **offset** - 缓冲区写入的写入偏移量
* **length** - 要从文件中读取的字节数
* **position** - 文件读取的起始位置，如果 position 的值为 null，则会从当前文件指针的位置读取
* **callback** - 回调函数，有三个参数err, bytesRead, buffer，err 为错误信息， bytesRead 表示读取的字节数，buffer 为缓冲区对象


#### 实例

```javascript

var fs = require("fs");
var buf = new Buffer.alloc(1024);

console.log("准备打开已存在的文件")
fs.open("input.txt", "r+", function(err, data) {
    if(err) return console.error(err);

    console.log("文件打开成功")
    console.log("准备读取文件")

    // data:通过 fs.open() 方法返回的文件描述符
    // buf:数据写入的缓冲区
    // 0:缓冲区写入的写入偏移量
    // buf.length:要从文件中读取的字节数
    // 0:文件读取的起始位置，如果 position 的值为 null，则会从当前文件指针的位置读取
    // function(err,bytes){}: 回调函数，有三个参数err, bytesRead, buffer，err 为错误信息， bytesRead 表示读取的字节数，buffer 为缓冲区对象
    fs.read(data,buf,0,buf.length,0,function(err,bytes) {
        if (err) return console.error(err);

        console.log(bytes + " 字节被读取")
        // 仅输出读取的字节
        if(bytes > 0) {
            console.log(buf.slice(0,bytes).toString())
        }
    })
})
```

输出结果

![65831581888](C:\Users\29787\AppData\Local\Temp\1658315818881.png)

### 关闭文件

#### 语法

```javascript
fs.close(fd, callback)
```

#### 参数

参数使用说明如下：

* **fd** - 通过 fs.open() 方法返回的文件描述符
* **callback** - 回调函数，没有参数。

#### 实例

```javascript
var fs = require("fs");
var buf = new Buffer.alloc(1024);

console.log("准备打开已存在的文件")
fs.open("input.txt", "r+", function(err, data) {
    if(err) return console.error(err);

    console.log("文件打开成功")
    console.log("准备读取文件")

    fs.read(data,buf,0,buf.length,0,function(err,bytes) {
        if (err) return console.error(err);

        // 仅输出读取的字节
        if(bytes > 0) {
            console.log(buf.slice(0,bytes).toString())
        }

        // 关闭文件
        fs.close(data,function(err) {
            if(err) return console.error(err);

            console.log("文件关闭成功")
        })
    })
})
```

输出结果

![65831605308](C:\Users\29787\AppData\Local\Temp\1658316053083.png)

### 截取文件

#### 语法

异步模式下截取文件的语法格式：

```javascript
fs.ftruncate(fd, len, callback)
```

该方法使用了文件描述符来读取文件

#### 参数

参数使用说明如下：

* **fd** - 通过 fs.open() 方法返回的文件描述符
* **len** - 文件内容截取的长度
* **callback** - 回调函数，没有参数。

#### 实例

```javascript
var fs = require("fs");
var buf = new Buffer.alloc(1024);

console.log("准备打开已存在的文件")
fs.open("input.txt", "r+", function(err, data) {
    if(err) return console.error(err);

    console.log("文件打开成功")
    console.log("截取10字节内的文件内容，超出部分将被去除");

    fs.ftruncate(data,10,function(err){
        if(err) return console.error(err)

        console.log("文件截取成功")
        console.log("读取相同的文件")

        fs.read(data,buf,0,buf.length,0,function(err,bytes) {
            if (err) return console.error(err);
    
            // 仅输出读取的字节
            if(bytes > 0) {
                console.log(buf.slice(0,bytes).toString())
            }
    
            // 关闭文件
            fs.close(data,function(err) {
                if(err) return console.error(err);
    
                console.log("文件关闭成功")
            })
        })
    })
})
```

输出结果

![65831645735](C:\Users\29787\AppData\Local\Temp\1658316457354.png)

### 删除文件

#### 语法

```javascript
fs.unlink(path, callback)
```

#### 参数

参数使用说明如下：

* **path** - 文件路径
* **callback** - 回调函数，没有参数。

#### 实例

```javascript
var fs = require("fs");

console.log("开始删除文件")

fs.unlink("output.txt",function(err) {
    if(err) return console.error(err)

    console.log("文件删除成功")
})
```

输出结果

![65831678537](C:\Users\29787\AppData\Local\Temp\1658316785373.png)

再去查看 output.txt 文件，发现已经不存在了。

### 创建目录

#### 语法

```javascript
fs.mkdir(path[, options], callback)
```

#### 参数

参数使用说明如下：

* **path** - 文件路径
* **options** 参数可以是：
  * **recursive** - 是否以递归的方式创建目录，默认为 false
  * **mode** - 设置目录权限，默认为 0777
* **callback** - 回调函数，没有参数。

#### 实例

```javascript
var fs = require("fs")

console.log("创建目录 D:/StudyData/nodeJS/test")

fs.mkdir("D:/StudyData/nodeJS/test",function(err) {
    if(err) return console.error(err)

    console.log("D:/StudyData/nodeJS/test 目录创建成功")
})
```

输出结果

![65831739153](C:\Users\29787\AppData\Local\Temp\1658317391536.png)

### 读取目录

#### 语法

```javascript
fs.readdir(path, callback)
```

#### 参数

参数使用说明如下：

* **path** - 文件路径
* **callback** - 回调函数，回调函数带有两个参数err, files，err 为错误信息，files 为 目录下的文件数组列表

#### 实例

```javascript
var fs = require("fs")

console.log("查看 D:/StudyData/nodeJS 目录")

fs.readdir("D:/StudyData/nodeJS/", function(err, files){
    if(err) console.error(err)

    files.forEach(function(file){
        console.log(file)
    })
})
```

输出结果

![65831767628](C:\Users\29787\AppData\Local\Temp\1658317676287.png)

### 删除目录

#### 语法

```javascript
fs.rmdir(path, callback)
```

#### 参数

参数使用说明如下：

* **path** - 文件路径
* **callback** - 回调函数，没有参数

#### 实例

```javascript
var fs = require("fs")

console.log("删除 D:/StudyData/nodeJS/test 目录")

fs.rmdir("D:/StudyData/nodeJS/test", function(err){
    if (err) return console.error(err)

    console.log("目录 D:/StudyData/nodeJS/test 删除成功")
    console.log("查看 D:/StudyData/nodeJS 目录")

    fs.readdir("D:/StudyData/nodeJS/", function(err, files){
        if(err) console.error(err)
    
        files.forEach(function(file){
            console.log(file)
        })
    })
})
```

输出结果

![65831793481](C:\Users\29787\AppData\Local\Temp\1658317934810.png)

### 文件模块方法参考手册

[Node.js 文件系统 | 菜鸟教程 (runoob.com)](https://www.runoob.com/nodejs/nodejs-fs.html)

## Node.js GET/POST请求

在很多场景中，我们的服务器都需要跟用户的浏览器打交道，如表单提交

表单提交到服务器一般都使用 GET/POST 请求。

### 获取 GET 请求内容

由于GET请求直接被嵌入在路径中，URL是完整的请求路径，包括了?后面的部分，因此你可以手动解析后面的内容作为GET请求的参数

#### 实例

```javascript
var http = require('http')
var url = require('url')
var util = require('util')

http.createServer(function(req, res) {
    res.writeHead(200, {"Content-Type": "text/plain;charset=utf-8"})
    res.end(util.inspect(url.parse(req.url,true)))
    
}).listen(8888)
```

### 获取URL参数

```javascript
var http = require('http')
var url = require('url')
var util = require('util')

http.createServer(function(req, res) {
    res.writeHead(200, {"Content-Type": "text/plain;charset=utf-8"})

    var parm = url.parse(req.url,true).query
    res.write("姓名：" + parm.name)
    res.write("\n")
    res.write("年龄：" + parm.age)
    res.end()

}).listen(8888)
```

浏览器输入：http://localhost:8888/user?name=%E6%9D%8E%E5%B9%B3&age=22

![65836639210](C:\Users\29787\AppData\Local\Temp\1658366392100.png)

### 获取POST请求的内容

POST 请求的内容全部的都在请求体中，http.ServerRequest 并没有一个属性内容为请求体，原因是等待请求体传输可能是一件耗时的工作

比如上传文件，而很多时候我们可能并不需要理会请求体的内容，恶意的POST请求会大大消耗服务器的资源，所以 node.js 默认是不会解析请求体的，当你需要的时候，需要手动来做。

#### 基本语法结构说明

```javascript
var http = require('http')
var querystring = require('querystring')
var util = require('util')

http.createServer(function(req, res) {
    // 定义post变量，用于暂存请求体的内容
    var post = ""

    // 通过req的data事件监听函数，每当接收到请求体的数据，就累加到post变量中
    req.on("data",function(d) {
        post += d
    })
    
    // 在end事件触发后，通过querystring.parse将post解析为真正的post请求，然后向客户端返回
    req.on("end",function(){
        post = querystring.parse(post)
        res.end(util.inspect(post))
    })
}).listen(8888)
```

#### 实例

```javascript
var http = require("http")
var querystring = require("querystring")

var postHTML = `<html><head><meta charset="utf-8"><title>用户信息</title></head>` +
                `<body>` +
                `<form method="post">` +
                `姓名 <input name="name"><br>` +
                `年龄： <input name="age"><br>` +
                `<input type="submit">` +
                `</form>` +
                `</body></html>`             

http.createServer(function(req, res) {
    var body = ""

    req.on("data", function(d) {
        body += d
    })
    req.on("end", function() {
        // 解析参数
        body = querystring.parse(body)
        // 设置响应头部信息及编码
        res.writeHead(200,{"Content-Type": "text/plain;charset=utf-8"})

        if(body.name && body.age){
            res.write("姓名：" + body.name)
            res.write("\n")
            res.write("年龄：" + body.age)
        }else{
            // 输出表单
            res.write(postHTML)
        }

        res.end()
    })
}).listen(8888)
```

输出结果

![65836811945](C:\Users\29787\AppData\Local\Temp\1658368119453.png)

## Node.js 工具模块

### OS模块

#### 方法

|            方法            | 描述                                                         |
| :------------------------: | :----------------------------------------------------------- |
|      **os.tmpdir()**       | 返回操作系统的默认临时文件夹                                 |
|    **os.endianness()**     | 返回 CPU 的字节序，可能是“BE”或“LE”                          |
|     **os.hostname()**      | 返回操作系统的主机名                                         |
|       **os.type()**        | 返回操作系统名                                               |
|     **os.platform()**      | 返回编译时的操作系统名                                       |
|       **os.arch()**        | 返回操作系统CPU架构 ，可能的值有 "x64"、"arm" 和 "ia32"      |
|      **os.release()**      | 返回操作系统的发行版本                                       |
|      **os.uptime()**       | 返回操作系统运行的时间，以秒为单位                           |
|      **os.loadavg()**      | 返回一个包含 1、5、15 分钟平均负载的数组                     |
|     **os.totalmem()**      | 返回系统内存总量，单位为字节                                 |
|      **os.freemem()**      | 返回操作系统空闲内存量，单位是字节                           |
|       **os.cpus()**        | 返回一个对象数组，包含所安装的每个 CPU/内核的信息：型号、速度（单位 MHz）、时间（一个包含 user、nice、sys、idle 和 irq 所使用 CPU/内核毫秒数的对象） |
| **os.networkInterfaces()** | 获得网络接口列表                                             |

#### 属性

|    属性    | 描述                         |
| :--------: | ---------------------------- |
| **os.EOL** | 定义了操作系统的行尾符的常量 |

### Path模块

```javascript
var path = require("path")
```

#### 方法

| 方法                                   | 描述                                                         |
| :------------------------------------- | :----------------------------------------------------------- |
| **path.normalize(p)**                  | 规范化路径，注意'..' 和 '.'                                  |
| **path.join([path1][, path2][, ...])** | 用于连接路径。该方法的主要用途在于，会正确使用当前系统的路径分隔符，Unix系统是"/"，Windows系统是"\" |
| **path.resolve([from ...], to)**       | 将 **to** 参数解析为绝对路径，给定的路径的序列是从右往左被处理的，后面每个 path 被依次解析，直到构造完成一个绝对路径。 例如，给定的路径片段的序列为：/foo、/bar、baz，则调用 path.resolve('/foo', '/bar', 'baz') 会返回 /bar/baz |
| **path.isAbsolute(path)**              | 判断参数 **path** 是否是绝对路径                             |
| **path.relative(from, to)**            | 用于将绝对路径转为相对路径，返回从 from 到 to 的相对路径（基于当前工作目录） |
| **path.dirname(p)**                    | 返回路径中代表文件夹的部分，同 Unix 的dirname 命令类似       |
| **path.basename(p[, ext])**            | 返回路径中的最后一部分。同 Unix 命令 bashname 类似           |
| **path.extname(p)**                    | 返回路径中文件的后缀名，即路径中最后一个'.'之后的部分。如果一个路径中并不包含'.'或该路径只包含一个'.' 且这个'.'为路径的第一个字符，则此命令返回空字符串 |
| **path.parse(pathString)**             | 返回路径字符串的对象                                         |
| **path.format(pathObject)**            | 从对象中返回路径字符串，和 path.parse 相反                   |

#### 属性

| 属性               | 描述                                                  |
| ------------------ | ----------------------------------------------------- |
| **path.sep**       | 平台的文件路径分隔符，'\\' 或 '/'                     |
| **path.delimiter** | 平台的分隔符, ; or ':'.                               |
| **path.posix**     | 提供上述 path 的方法，不过总是以 posix 兼容的方式交互 |
| **path.win32**     | 提供上述 path 的方法，不过总是以 win32 兼容的方式交互 |

### Net模块

[Node.js Net 模块 | 菜鸟教程 (runoob.com)](https://www.runoob.com/nodejs/nodejs-net-module.html)

### DNS模块

[Node.js DNS 模块 | 菜鸟教程 (runoob.com)](https://www.runoob.com/nodejs/nodejs-dns-module.html)

### Domain模块

[Node.js Domain 模块 | 菜鸟教程 (runoob.com)](https://www.runoob.com/nodejs/nodejs-domain-module.html)

## Node.js Web 模块

### 什么是Web服务器

Web服务器一般指网站服务器，是指驻留于因特网上某种类型计算机的程序，Web服务器的基本功能就是提供Web信息浏览服务。它只需支持HTTP协议、HTML文档格式及URL，与客户端的网络浏览器配合

大多数 web 服务器都支持服务端的脚本语言（php、python、ruby）等，并通过脚本语言从数据库获取数据，将结果返回给客户端浏览器

目前最主流的三个Web服务器是Apache、Nginx、IIS

### Web应用框架

![65836965732](C:\Users\29787\AppData\Local\Temp\1658369657328.png)

* **Client** - 客户端，一般指浏览器，浏览器可以通过 HTTP 协议向服务器请求数据
* **Server** - 服务端，一般指 Web 服务器，可以接收客户端请求，并向客户端发送响应数据
* **Business** - 业务层， 通过 Web 服务器处理应用程序，如与数据库交互，逻辑运算，调用外部程序等
* **Data** - 数据层，一般由数据库组成

### 使用 Node 创建 Web 服务器

Node.js 提供了 http 模块，http 模块主要用于搭建 HTTP 服务端和客户端，使用 HTTP 服务器或客户端功能必须调用 http 模块，代码如下：

```javascript
var http = require("http)
```

#### 实例

server.js

```javascript
var http = require('http');
var fs = require('fs');
var url = require('url');
 
// 创建服务器
http.createServer( function (request, response) {  
   // 解析请求，包括文件名
   var pathname = url.parse(request.url).pathname;
   
   // 输出请求的文件名
   console.log("Request for " + pathname + " received.");
   
   // 从文件系统中读取请求的文件内容
   fs.readFile(pathname.substr(1), function (err, data) {
      if (err) {
         console.log(err);
         // HTTP 状态码: 404 : NOT FOUND
         // Content Type: text/html
         response.writeHead(404, {'Content-Type': 'text/html'});
      }else{             
         // HTTP 状态码: 200 : OK
         // Content Type: text/html
         response.writeHead(200, {'Content-Type': 'text/html'});    
         
         // 响应文件内容
         response.write(data.toString());        
      }
      //  发送响应数据
      response.end();
   });   
}).listen(8888);
 
// 控制台会输出以下信息
console.log('Server running at http://127.0.0.1:8888/');
```

index.html

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>NodeJs</title>
    </head>
    <body>
        <h1>我的第一个标题</h1>
        <p>我的第一个段落。</p>
    </body>
</html>
```

浏览器输入：http://localhost:8888/index.html

![65837050365](C:\Users\29787\AppData\Local\Temp\1658370503652.png)

控制台输出：

![65837054676](C:\Users\29787\AppData\Local\Temp\1658370546768.png)



### 使用 Node 创建 Web 客户端

#### 实例

```javascript
var http = require('http')

// 用于请求的选项
var options = {
    host: 'localhost',
    port: 8888,
    path: '/index.html',
}

// 处理响应的回调函数
var callback = function(res){
    // 更新数据
    var body = ""

    res.on("data", function(d){
        body += d
    })
    res.on("end", function(){
        // 数据接收完成
        console.log(body)
    })
}

// 向服务端发送请求
var req = http.request(options,callback)

req.end()
```

客户端输出结果

![65837092390](C:\Users\29787\AppData\Local\Temp\1658370923900.png)

服务端输出结果

![65837096201](C:\Users\29787\AppData\Local\Temp\1658370962017.png)

## Node.js Express 框架

### 简介

Express 是一个简洁而灵活的 node.js Web应用框架, 提供了一系列强大特性帮助你创建各种 Web 应用，和丰富的 HTTP 工具。

使用 Express 可以快速地搭建一个完整功能的网站。

Express 框架核心特性：

* 可以设置中间件来响应 HTTP 请求
* 定义了路由表用于执行不同的 HTTP 请求动作
* 可以通过向模板传递参数来动态渲染 HTML 页面。

### 安装Express

```javascript
npm install express --save
```

以下几个重要的模块是需要与 express 框架一起安装的：

* **body-parser** - node.js 中间件，用于处理 JSON, Raw, Text 和 URL 编码的数据
* **cookie-parser** - 这就是一个解析Cookie的工具。通过req.cookies可以取到传过来的cookie，并把它们转成对象
* **multer** - node.js 中间件，用于处理 enctype="multipart/form-data"（设置表单的MIME编码）的表单数据

```javascript
npm install body-parser --save
npm install cookie-parser --save
npm install multer --save
```

### 第一个 Express 实例

```javascript
var express = require('express')
var app = express()

app.get("/", function(req, res) {
    res.send("Hello World!")
})

var server = app.listen(8888, function(){
    var host = server.address().address
    var port = server.address().port

    console.log("应用实例，访问地址为 http://%s:%s", host, port)
})
```

浏览器输入：http://localhost:8888/

![65837250919](C:\Users\29787\AppData\Local\Temp\1658372509195.png)

### 请求和响应

Express 应用使用回调函数的参数： **request** 和 **response** 对象来处理请求和响应的数据

```javascript
app.get('/', function (req, res) {
   // ---
})
```

**request** 和 **response** 对象的具体介绍：

**Request 对象** - request 对象表示 HTTP 请求，包含了请求查询字符串，参数，内容，HTTP 头部等属性。常见属性有：

1. req.app：当callback为外部文件时，用req.app访问express的实例
2. req.baseUrl：获取路由当前安装的URL路径
3. req.body / req.cookies：获得「请求主体」/ Cookies
4. req.fresh / req.stale：判断请求是否还「新鲜」
5. req.hostname / req.ip：获取主机名和IP地址
6. req.originalUrl：获取原始请求URL
7. req.params：获取路由的parameters
8. req.path：获取请求路径
9. req.protocol：获取协议类型
10. req.query：获取URL的查询参数串
11. req.route：获取当前匹配的路由
12. req.subdomains：获取子域名
13. req.accepts()：检查可接受的请求的文档类型
14. req.acceptsCharsets / req.acceptsEncodings / req.acceptsLanguages：返回指定字符集的第一个可接受字符编码
15. req.get()：获取指定的HTTP请求头
16. req.is()：判断请求头Content-Type的MIME类型

**Response 对象** - response 对象表示 HTTP 响应，即在接收到请求时向客户端发送的 HTTP 响应数据。常见属性有：

1. res.app：同req.app一样
2. res.append()：追加指定HTTP头
3. res.set()在res.append()后将重置之前设置的头
4. res.cookie(name，value [，option])：设置Cookie
5. opition: domain / expires / httpOnly / maxAge / path / secure / signed
6. res.clearCookie()：清除Cookie
7. res.download()：传送指定路径的文件
8. res.get()：返回指定的HTTP头
9. res.json()：传送JSON响应
10. res.jsonp()：传送JSONP响应
11. res.location()：只设置响应的Location HTTP头，不设置状态码或者close response
12. res.redirect()：设置响应的Location HTTP头，并且设置状态码302
13. res.render(view,[locals],callback)：渲染一个view，同时向callback传递渲染后的字符串，如果在渲染过程中有错误发生next(err)将会被自动调用。callback将会被传入一个可能发生的错误以及渲染后的页面，这样就不会自动输出了。
14. res.send()：传送HTTP响应
15. res.sendFile(path [，options][，fn])：传送指定路径的文件 -会自动根据文件extension设定Content-Type
16. res.set()：设置HTTP头，传入object可以一次设置多个头
17. res.status()：设置HTTP状态码
18. res.type()：设置Content-Type的MIME类型

### 路由

```javascript
var express = require('express')
var app = express()

// 主页
app.get("/", function(req, res) {
    console.log("主页 GET 请求");
    res.send("Hello World!")
})

// post 请求
app.post("/", function(req, res) {
    console.log("主页 POST 请求");
    res.send('Hello POST');
})

// user页面响应
app.get("/user", function(req, res) {
    console.log("/user 响应")
    res.send("User")
})

// 对页面 abcd, abxcd, ab123cd, 等响应 GET 请求
app.get("/ab*cd", function(req, res) {
    console.log("/ab*cd 响应")
    res.send("正则匹配")
})

var server = app.listen(8888, function(){

    var host = server.address().address
    var port = server.address().port

    console.log("应用实例，访问地址为 http://%s:%s", host, port)
})
```

浏览器输入：http://localhost:8888/

![65837324799](C:\Users\29787\AppData\Local\Temp\1658373247995.png)

浏览器输入：http://localhost:8888/user

![65837327472](C:\Users\29787\AppData\Local\Temp\1658373274720.png)

浏览器输入：http://localhost:8888/abcegcd

![65837330783](C:\Users\29787\AppData\Local\Temp\1658373307834.png)

浏览器输入：http://localhost:8888/fert45

![65837334513](C:\Users\29787\AppData\Local\Temp\1658373345130.png)

### 静态文件

Express 提供了内置的中间件 **express.static** 来设置静态文件，如：图片， CSS, JavaScript 等。

你可以使用 **express.static** 中间件来设置静态文件路径。例如，如果你将图片， CSS, JavaScript 文件放在 public 目录下，你可以这么写：

```javascript
app.use("/public",express.static("public"))
```

### GET方法

index.html

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>NodeJs</title>
    </head>
    <body>
        <form action="http://127.0.0.1:8888/process_get" method="GET">
            First Name: <input type="text" name="first_name">  <br>
             
            Last Name: <input type="text" name="last_name">
            <input type="submit" value="Submit">
            </form>
    </body>
</html>
```

process_get_.js

```javascript
var express = require('express')
var app = express()

app.use("public",express.static("public"))

app.get("/index.html", function(req, res){
    res.sendFile(__dirname + "/" + "index.html")
})

app.get("/process_get", function(req, res){
    // 输出 JSON 格式数据
    var json = {
        "first_name": req.query.first_name,
        "last_name": req.query.last_name
    }

    console.log(json)

    res.send(JSON.stringify(json))
})

var server = app.listen(8888, function(){

    var host = server.address().address
    var port = server.address().port

    console.log("应用实例，访问地址为 http://%s:%s", host, port)
})
```

浏览器输入：http://127.0.0.1:8888/index.html

填写表单，点击 Submit 按钮

![65837443226](C:\Users\29787\AppData\Local\Temp\1658374432261.png)

![65837449393](C:\Users\29787\AppData\Local\Temp\1658374493935.png)

控制台输出

![65837452321](C:\Users\29787\AppData\Local\Temp\1658374523210.png)



### POST方法

index.html

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>NodeJs</title>
    </head>
    <body>
        <form action="http://127.0.0.1:8888/process_get" method="POST">
            First Name: <input type="text" name="first_name">  <br>
             
            Last Name: <input type="text" name="last_name">
            <input type="submit" value="Submit">
            </form>
    </body>
</html>
```

process_post.js

```javascript
var express = require('express')
var app = express()
var bodyParser = require('body-parser')

// 创建 application/x-www-form-urlencoded 编码解析
var urlencodedParser = bodyParser.urlencoded({extended: false})

app.use("public",express.static("public"))

app.get("/index.html", function(req, res){
    res.sendFile(__dirname + "/" + "index.html")
})

app.post("/process_post", urlencodedParser, function(req, res){

    // 输出 JSON 格式数据
    var json = {
        "first_name": req.body.first_name,
        "last_name": req.body.last_name
    }

    console.log(json)
    res.send(JSON.stringify(json))
})

var server = app.listen(8888, function(){

    var host = server.address().address
    var port = server.address().port

    console.log("应用实例，访问地址为 http://%s:%s", host, port)
})
```

浏览器输入：http://127.0.0.1:8888/index.html

填写表单

![65837859647](C:\Users\29787\AppData\Local\Temp\1658378596474.png)



点击submit

![65837863848](C:\Users\29787\AppData\Local\Temp\1658378638485.png)

控制台输出

![65837865897](C:\Users\29787\AppData\Local\Temp\1658378658973.png)

### 文件上传

file.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>文件上传</title>
</head>
<body>
    <h3>文件上传：</h3>
    选择一个文件上传: <br />
    <form action="/file_upload" method="post" enctype="multipart/form-data">
        <input type="file" name="image" size="50" />
        <br />
        <input type="submit" value="上传文件" />
    </form>
</body>
</html>
```

file_upload.js

```javascript
var express = require('express')
var fs = require('fs')
var bodyParser = require('body-parser')
var multer = require('multer')

var app = express()
app.use("/public", express.static("public"))
app.use(bodyParser.urlencoded({ extended: false}))
app.use(multer({dest: "/tmp/"}).array("image"))

app.get("/file.html", function (req, res) {
    res.sendFile(__dirname + "/file.html")
})

app.post("/file_upload", function (req, res) {

    console.log("Uploading file..." + req.files[0])
    var des_file = __dirname + "/" + req.files[0].originalname

    fs.readFile(req.files[0].path, function (err,data){
        fs.writeFile(des_file, data, function (err){
            if (err) return console.error(err)

            response = {
                message: "File uploaded successfully",
                filename: req.files[0].originalname
            }

            console.log( response )
            res.end( JSON.stringify( response ) )
        })
    })
})

var server = app.listen(8888, function () {

    var host = server.address().address
    var port = server.address().port

    console.log("应用实例，访问地址为 http://%s:%s", host, port)
})
```

### Cookie管理

#### 示例

```javascript
var express = require('express')
var cookieParser = require('cookie-parser')
var util = require('util')

var app = express()
app.use(cookieParser())

app.get('/', function(req, res) {
    console.log("Cookie: " + util.inspect(req.cookies))
})

app.listen(8888)
```

## Node.js RESTful API

### 什么是 REST

REST即表述性状态传递（英文：Representational State Transfer，简称REST）是Roy Fielding博士在2000年他的博士论文中提出来的一种软件架构风格。

表述性状态转移是一组架构约束条件和原则。满足这些约束条件和原则的应用程序或设计就是RESTful。需要注意的是，REST是设计风格而不是标准。REST通常基于使用HTTP，URI，和XML（标准通用标记语言下的一个子集）以及HTML（标准通用标记语言下的一个应用）这些现有的广泛流行的协议和标准。REST 通常使用 JSON 数据格式。

### HTTP方法

以下为 REST 基本架构的四个方法：

* **GET** - 用于获取数据
* **PUT** - 用于更新或添加数据
* **DELETE** - 用于删除数据。
* **POST** - 用于添加数据

### 创建 RESTFUL

#### 创建 users.json

```json
{
    "user1" : {
       "name" : "mahesh",
       "password" : "password1",
       "profession" : "teacher",
       "id": 1
    },
    "user2" : {
       "name" : "suresh",
       "password" : "password2",
       "profession" : "librarian",
       "id": 2
    },
    "user3" : {
       "name" : "ramesh",
       "password" : "password3",
       "profession" : "clerk",
       "id": 3
    }
 }
```

### 获取用户列表

```javascript
var express = require('express')
var app = express()
var fs = require('fs')

// 获取用户列表信息
app.get("/user_list", function(req, res) {
    fs.readFile(__dirname + '/users.json',"utf8", function(err, data) {
        if (err) return console.error(err)

        console.log(data)
        res.end(data)
    })
})

var server = app.listen(8888, function(){

    var host = server.address().address
    var port = server.address().port

    console.log("应用实例，访问地址为 http://%s:%s", host, port)
})
```

浏览器输入：http://127.0.0.1:8888/user_list

![65838254318](C:\Users\29787\AppData\Local\Temp\1658382543189.png)

### 显示用户详情

```javascript
var express = require('express')
var app = express()
var fs = require('fs')

// 获取用户详情
app.get("/user_detail/:id", function(req, res) {
    fs.readFile(__dirname + "/users.json", "utf8", function(err,data){
        
        data = JSON.parse(data)
        var u = data["user" + req.params.id]
        console.log(u)

        res.end(JSON.stringify(u))
    })
})

var server = app.listen(8888, function(){

    var host = server.address().address
    var port = server.address().port

    console.log("应用实例，访问地址为 http://%s:%s", host, port)
})
```

浏览器输入：http://127.0.0.1:8888/user_detail/1

![65838279659](C:\Users\29787\AppData\Local\Temp\1658382796593.png)



### 添加用户

```javascript
var express = require('express')
var app = express()
var fs = require('fs')

// 添加用户信息
var user = {
    "user4" : {
        "name" : "mohit",
        "password" : "password4",
        "profession" : "teacher",
        "id": 4
     }
}

app.get("/add_user", function(req, res) {
    fs.readFile(__dirname + "/users.json", "utf8", function(err, data) {
        data = JSON.parse(data)

        data["user4"] = user["user4"]
        console.log(data)

        res.end(JSON.stringify(data))
    })
})

var server = app.listen(8888, function(){

    var host = server.address().address
    var port = server.address().port

    console.log("应用实例，访问地址为 http://%s:%s", host, port)
})
```

浏览器输入：http://127.0.0.1:8888/add_user

![65838299105](C:\Users\29787\AppData\Local\Temp\1658382991055.png)

### 删除用户

```javascript
var express = require('express')
var app = express()
var fs = require('fs')

// 删除用户
app.get("/del_user/:id", function(req, res) {
    fs.readFile(__dirname + "/users.json", "utf8", function(err, data) {
        
        data = JSON.parse(data)
        delete data["user" + req.params.id]
        console.log(data)

        res.end(JSON.stringify(data))
    })
})

var server = app.listen(8888, function(){

    var host = server.address().address
    var port = server.address().port

    console.log("应用实例，访问地址为 http://%s:%s", host, port)
})
```

浏览器输入：http://127.0.0.1:8888/del_user/3

![65838309783](C:\Users\29787\AppData\Local\Temp\1658383097835.png)

## Node.js 多进程

我们都知道 Node.js 是以单线程的模式运行的，但它使用的是事件驱动来处理并发，这样有助于我们在多核 cpu 的系统上创建多个子进程，从而提高性能。

每个子进程总是带有三个流对象：child.stdin, child.stdout 和child.stderr。他们可能会共享父进程的 stdio 流，或者也可以是独立的被导流的流对象。

Node 提供了 child_process 模块来创建子进程，方法有：

* **exec** - child_process.exec 使用子进程执行命令，缓存子进程的输出，并将子进程的输出以回调函数参数的形式返回
* **spawn** - child_process.spawn 使用指定的命令行参数创建新进程
* **fork** - child_process.fork 是 spawn()的特殊形式，用于在子进程中运行的模块，如 fork('./son.js') 相当于 spawn('node', ['./son.js']) 。与spawn方法不同的是，fork会在父进程与子进程之间，建立一个通信管道，用于进程之间的通信。

### exec() 方法

child_process.exec 使用子进程执行命令，缓存子进程的输出，并将子进程的输出以回调函数参数的形式返回

#### 语法

```javascript
child_process.exec(command[, options], callback)
```

#### 参数

参数说明如下：

**command：** 字符串， 将要运行的命令，参数使用空格隔开

**options ：对象，可以是：**

* cwd ，字符串，子进程的当前工作目录
* env，对象 环境变量键值对
* encoding ，字符串，字符编码（默认： 'utf8'）
* shell ，字符串，将要执行命令的 Shell（默认: 在 UNIX 中为`/bin/sh`， 在 Windows 中为`cmd.exe`， Shell 应当能识别 `-c`开关在 UNIX 中，或 `/s /c` 在 Windows 中。 在Windows 中，命令行解析应当能兼容`cmd.exe`）
* timeout，数字，超时时间（默认： 0）
* maxBuffer，数字， 在 stdout 或 stderr 中允许存在的最大缓冲（二进制），如果超出那么子进程将会被杀死 （默认: 200*1024）
* killSignal ，字符串，结束信号（默认：'SIGTERM'）
* uid，数字，设置用户进程的 ID
* gid，数字，设置进程组的 ID

**callback ：**回调函数，包含三个参数error, stdout 和 stderr

exec() 方法返回最大的缓冲区，并等待进程结束，一次性返回缓冲区的内容

#### 实例

process.js

```javascript
const fs = require('fs')
const child_process = require('child_process')

for(var i = 0;i < 4;i++) {
    var workerProcess = child_process.exec("node support.js" + i, function(err, stdout, stderr) {
        if(err) {
            console.log(err.stack)
            console.log("Error code: " + err.code)
            console.log("Signal received: " + err.signal)
        }
        console.log('stdout: ' + stdout);
        console.log('stderr: ' + stderr);
    })
    workerProcess.on("exit", function(code) {
        console.log('子进程已退出，退出码 ' + code)
    })
}
```

support.js

```javascript
console.log("进程 " + process.argv[2] + " 执行。" )
```

### spawn() 方法

child_process.spawn 使用指定的命令行参数创建新进程

#### 语法

```javascript
child_process.spawn(command[, args][, options])
```

#### 参数

参数说明如下：

**command：** 将要运行的命令

**args：** Array 字符串参数数组

**options Object**

* cwd String 子进程的当前工作目录
* env Object 环境变量键值对
* stdio Array|String 子进程的 stdio 配置
* detached Boolean 这个子进程将会变成进程组的领导
* uid Number 设置用户进程的 ID
* gid Number 设置进程组的 ID

spawn() 方法返回流 (stdout & stderr)，在进程返回大量数据时使用。进程一旦开始执行时 spawn() 就开始接收响应

#### 实例

```javascript
const fs = require('fs');
const child_process = require('child_process');
 
for(var i=0; i<3; i++) {
   var workerProcess = child_process.spawn('node', ['support.js', i]);
 
   workerProcess.stdout.on('data', function (data) {
      console.log('stdout: ' + data);
   });
 
   workerProcess.stderr.on('data', function (data) {
      console.log('stderr: ' + data);
   });
 
   workerProcess.on('close', function (code) {
      console.log('子进程已退出，退出码 '+code);
   });
}
```

![65838484625](C:\Users\29787\AppData\Local\Temp\1658384846253.png)

### fork 方法

child_process.fork 是 spawn() 方法的特殊形式，用于创建进程

#### 语法

```javascript
child_process.fork(modulePath[, args][, options])
```

#### 参数

参数说明如下：

**modulePath**： String，将要在子进程中运行的模块

**args**： Array 字符串参数数组

**options**：Object

* cwd String 子进程的当前工作目录
* env Object 环境变量键值对
* execPath String 创建子进程的可执行文件
* execArgv Array 子进程的可执行文件的字符串参数数组（默认： process.execArgv）
* silent Boolean 如果为`true`，子进程的`stdin`，`stdout`和`stderr`将会被关联至父进程，否则，它们将会从父进程中继承。（默认为：`false`）
* uid Number 设置用户进程的 ID
* gid Number 设置进程组的 ID

返回的对象除了拥有ChildProcess实例的所有方法，还有一个内建的通信信道

#### 实例

```javascript
const fs = require('fs');
const child_process = require('child_process');
 
for(var i=0; i<3; i++) {
   var worker_process = child_process.fork("support.js", [i]);    
 
   worker_process.on('close', function (code) {
      console.log('子进程已退出，退出码 ' + code);
   });
}
```

![65838507429](C:\Users\29787\AppData\Local\Temp\1658385074294.png)

## Node.js JXcore 打包

Node.js 是一个开放源代码、跨平台的、用于服务器端和网络应用的运行环境

JXcore 是一个支持多线程的 Node.js 发行版本，基本不需要对你现有的代码做任何改动就可以直接线程安全地以多线程运行。

### JXcore 安装

[Node.js JXcore 打包 | 菜鸟教程 (runoob.com)](https://www.runoob.com/nodejs/nodejs-jxcore-packaging.html)

### 包代码

#### 语法

```javascript
jx package index.js index
```

### 载入 JX 文件

node.js 的项目运行

```javascript
node index.js command_line_arguments
```

使用 **JXcore** 编译后，我们可以使用以下命令来执行生成的 jx 二进制文件

```javascript
jx index.jx command_line_arguments
```

## Node.js 连接 MySQL

### 安装驱动

```javascript
npm install mysql
```

### 连接数据库

```javascript
// 连接 mysql 数据库
var mysql = require("mysql")

// 数据库连接配置信息
var connection = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "wsbkhs2019",
    database: "node"
})

// 连接数据库
connection.connect()

// 数据库查询
connection.query("SELECT * FROM test", function(err, results, fields) {
    if(err) throw err

    console.log(results)
})
```

### 查询数据

```javascript
// 连接 mysql 数据库
var mysql = require("mysql")

// 数据库连接配置信息
var connection = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "wsbkhs2019",
    database: "node"
})

// 连接数据库
connection.connect()

// 数据库查询
connection.query("SELECT * FROM test", function(err, results, fields) {
    if(err) throw err

    console.log(results)
})

connection.end()
```

### 插入数据

```javascript
// 连接 mysql 数据库
var mysql = require("mysql")

// 数据库连接配置信息
var connection = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "wsbkhs2019",
    database: "node"
})

// 连接数据库
connection.connect()

// 添加数据
var sql = "INSERT INTO test(id,name,age) VALUES(2,?,?)"
var param = ["啦啦",25]

connection.query(sql, param, function(err, results) {
    if(err) return console.error(err)

    console.log(results)
})

connection.end()
```

### 更新数据

```javascript
// 连接 mysql 数据库
var mysql = require("mysql")

// 数据库连接配置信息
var connection = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "wsbkhs2019",
    database: "node"
})

// 连接数据库
connection.connect()

// 更新数据
var uploadSql = "UPDATE test SET age=? WHERE id=?"
var uploadParams = [33,1]

connection.query(uploadSql, uploadParams, function(err, results){
    if(err) return console.error(err)

    console.log(results)
})

connection.end()
```

### 删除数据

```javascript
// 连接 mysql 数据库
var mysql = require("mysql")

// 数据库连接配置信息
var connection = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "wsbkhs2019",
    database: "node"
})

// 连接数据库
connection.connect()

// 删除数据
var delSql = "DELETE FROM test WHERE id=2"

connection.query(delSql, function(err, results){
    if(err) return console.error(err)

    console.log(results)
})

connection.end()
```

