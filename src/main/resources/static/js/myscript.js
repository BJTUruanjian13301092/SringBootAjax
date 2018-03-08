/**
 * Created by Intellij IDEA.
 * @Author LUOLIANG
 * @Date 2016/8/2
 * @Comment js文件，用于页面发送ajax请求
 */

//定义一个avalonjs的控制器
var viewmodel = avalon.define({
    //id必须和页面上定义的ms-controller名字相同，否则无法控制页面
    $id: "viewmodel",
    datalist: {},
    text: "请求数据",
    text2: "Guess What",

    request: function () {
        $.ajax({
            type: "post",
            url: "/hello/data",    //向springboot请求数据的url
            data: {},
            success: function (data) {
                $('button').removeClass("btn-primary").addClass("btn-success").attr('disabled', true);

                viewmodel.datalist = data;

                viewmodel.text = "数据请求成功，已渲染";
            }
        });
    },

    request2: function () {
        $.ajax({
            type: "post",
            url: "/hello/guess",    //向springboot请求数据的url
            data: "firstnumber="+1+"&secondnumber="+1,
            async: false,

            success: function (msg) {
                if(msg == "Success"){
                    alert(msg);
                    window.location.href = "success";
                    return false;
                }
            },

            error:function(er){
                alert(er.message);
            }
        });
        return false;
    },

    request3: function () {
        $.ajax({
            type: "get",
            url: "/hello/success",    //向springboot请求数据的url
            data: {},
            success: function (msg) {
                alert(msg);
                window.location.href = "http://www.baidu.com";
                return false;
            }
        });
        return false;
    },

    request4: function () {
        $.ajax({
            type: "post",
            url: "/hello/json",    //向springboot请求数据的url
            data: '{"userName":"Larry","pwd":"13301092"}',
            dataType:"json",
            contentType:"application/json",

            success: function (msg) {
                if(msg == "Success"){
                    alert(msg);
                    window.location.href = "success";
                    return false;
                }
            },

            error:function(er){
                alert("error:" + er.message);
            }
        });
        return false;
    }



});