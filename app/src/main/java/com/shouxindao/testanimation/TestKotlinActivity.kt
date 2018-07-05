package com.shouxindao.testanimation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.xuyao.prancelib.util.ToastUtil
import kotlinx.android.synthetic.main.activity_testkotlin.*

class TestKotlinActivity : AppCompatActivity() {

    class  TestKotlinActivity(){
        init {
            //初始化代码块
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testkotlin)
        testTv.text = "我的测试赋值字符串"

        var getString: String = testTv.text.toString()
        var b = TestBean()

        testTv.setOnClickListener {
            ToastUtil.toast(this, b.test)
        }

        listTest()

        loopTest()
    }


    class TestBean {
        var test: String? = null
    }

    /**
     * while的循环应用
     */
    fun test(b:Any?):Int {
//        if(b is String)return 0
//        else return 1
        while (b is String){
            return 0
        }
        return 1
    }


    /**
     * list的生成和循环下标或者值
     */
    fun listTest(){
        var datas= listOf("1","2","3")
        //得到具体的下标
        for(data in datas.indices){
            println(data)
        }
        //得到具体值
        for(data in datas){
            println(data)
        }

    }

    /**
     * when用法一定要用到else
     */
    fun whenTest(b:String):String = when(b){
        "2"-> "one"
        "one"-> "two"
        else -> "three"
    }

    /**
     * 检查某个值在某个范围
     */
    fun findIntValue(num:Int?){
        if(num in 1..56){
            return
        }

    }



    fun loopTest(){
        tag@ for(i in 1..9){
            println("i=$i")
            uop@ for(j in 5..10){
                if(j==8){
//                    break@tag
                    continue@uop
                }
                println("j=$j")
            }

        }
    }


    fun forEach():Int{
        var ints:IntArray= intArrayOf(1,2,3,4,5,6,8)
        ints.forEach {
            if(it==5) return 5
        }
        return 0
    }
}
