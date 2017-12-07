package com.woodys.demos

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.woodys.demos.flowlayout.adapter.TextAdapter
import kotlinx.android.synthetic.main.activity_flow1.*
import java.util.*

class FlowLayout1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flow1)

        val strArray = "我每天6点多就起床，挤两个小时地铁，顶着烈日游走在各个人才市场。普通大专毕业在这个时代，在北京遍地都是。一天投上百份简历，接到面试电话却很少。好不容易接到面试电话，却与所学专业完全不对口。夜幕降临时，我拖着疲惫的身躯倚在地铁的栏杆上歇息。出了地铁，听见肚子咕咕的叫声我才想起一天都没吃东西了，赶紧在街头买两个包子充饥。我飞奔着跑回住处，抓紧洗漱入睡。我知道明天又将奋战一天半个月后我在新网世纪（北京）网络科技有限公司找到满意的工作，签订劳动合同。开始上班。老板拍着我的肩膀说他们不看重学历。只注重能力，公司需要像我这样的人才。好好干，有大好前途。".toCharArray()
        val items = ArrayList<String>()
        (0..100).forEach {
            var textVal = ""
            val textRandom = Random().nextInt(150)
            for (index in 0..textRandom) textVal += strArray[Random().nextInt(100)]
            items.add("$it、$textVal")
        }

        flowLayoutView.setAdapter(TextAdapter(items))
    }
}
