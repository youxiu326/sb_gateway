
###### cloud gateway 个人例子 


参考：

https://github.com/SpringCloud/spring-cloud-code/tree/master/ch18-7

<br/>



`
post json localhost:8080/route/add

{
    "filters":[

    ],
    "id":"jd_route",
    "order":0,
    "predicates":[
        {
            "args":{
                "pattern":"/jd"
            },
            "name":"Path"
        }
    ],
    "uri":"http://www.jd.com"
}
`