package com.forward.informationorderr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val arrayDirectories = arrayListOf(
            "node1|node2|node3",
            "node1|node2",
            "node1|node2|node3",
            "node1|node2|node3|node4",
            "node1|node22|node33|body.pdf",
            "node9|node8|perra.pdf"
        )

        val arrayFolders = ArrayList<String>()
        for (directory in arrayDirectories){
            val parts = directory.split("|")
            for ((index,value) in parts.withIndex()){
                if (value !in arrayFolders){
                    if (index == 0){
                        val cardView = createCardViewContainer(value)
                        main_container.addView(cardView)
                    }else if(!value.contains(".")){
                        val previousPart = parts[index-1]
                        val cardView = createCardViewContainer(value)

                        val linearLayoutParentExpandable =
                            main_container.findViewWithTag<LinearLayout>(previousPart+"expandable")
                        linearLayoutParentExpandable.addView(cardView)

                    }else{
                        val previousPart = parts[index-1]
                        val cardViewFile = createCardViewFile(value)
                        val linearLayoutParentExpandable =
                            main_container.findViewWithTag<LinearLayout>(previousPart+"expandable")
                        linearLayoutParentExpandable.addView(cardViewFile)
                    }
                    arrayFolders.add(value)
                }
            }
        }

    }
    private fun createCardViewFile(value: String):CardView{
        val cardView = createCardView(value)
        cardView.setPadding(15,15,15,15)
        val textView = createTexViewFile(value)
        cardView.addView(textView)
        return  cardView
    }

    private fun createCardViewContainer(value : String):CardView{
        val linearLayoutTitle =  createLinearLayoutTitle(value,value)
        val linearLayoutParent = createLinearLayoutParent(value)
        val linearLayoutParentExpandable = createParentExpandable(value)
        linearLayoutParent.addView(linearLayoutTitle)
        linearLayoutParent.addView(linearLayoutParentExpandable)
        val btn = linearLayoutTitle.findViewWithTag<Button>(value+"button")
        addFunSetOnclickListener(linearLayoutTitle,linearLayoutParentExpandable,btn)
        val cardView = createCardView(value)
        cardView.addView(linearLayoutParent)

        return cardView
    }

    private fun createCardView(tag: String): CardView {
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(10,10,10,10)

        val cardView = CardView(this)
        cardView.id = CardView.generateViewId()
        cardView.tag = tag
        cardView.layoutParams = params

        return cardView
    }

    private fun createLinearLayoutParent(tag: String):LinearLayout{
        val linearLayoutParent = LinearLayout(this)
        linearLayoutParent.id = LinearLayout.generateViewId()
        linearLayoutParent.tag = tag+"parent"
        linearLayoutParent.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        )
        linearLayoutParent.orientation = LinearLayout.VERTICAL
        return  linearLayoutParent
    }

    private fun createParentExpandable(tag: String):LinearLayout{
        val linearLayoutParent = LinearLayout(this)
        linearLayoutParent.id = LinearLayout.generateViewId()
        linearLayoutParent.tag = tag+"expandable"
        linearLayoutParent.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        )
        linearLayoutParent.orientation = LinearLayout.VERTICAL
        linearLayoutParent.visibility = View.GONE
        return  linearLayoutParent
    }

    private fun createLinearLayoutTitle(title:String,tag: String):LinearLayout{

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        val paramsTextView = LinearLayout.LayoutParams(
            0,
            convertFloatToDp(40f), 0.9f
        )

        val linearLayoutTitle = LinearLayout(this)
        linearLayoutTitle.id = View.generateViewId()
        linearLayoutTitle.tag = tag+"title"
        linearLayoutTitle.layoutParams = params
        linearLayoutTitle.orientation = LinearLayout.HORIZONTAL
        linearLayoutTitle.gravity = Gravity.CENTER
        linearLayoutTitle.setPadding(15,15,15,15)

        val textView = TextView(this)
        textView.layoutParams = paramsTextView
        textView.text = title
        textView.gravity = Gravity.CENTER


        val paramsButton = LinearLayout.LayoutParams(
            0,
            convertFloatToDp(30f), 0.1f
        )
        val button = Button(this)
        button.id = Button.generateViewId()
        button.tag = tag+"button"
        button.layoutParams = paramsButton
        button.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp)

        linearLayoutTitle.addView(textView)
        linearLayoutTitle.addView(button)
        return linearLayoutTitle
    }

    private fun createTexViewFile(tag: String):TextView{
        val paramsTextView = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            convertFloatToDp(40f)
        )
        val textView = TextView(this)
        textView.layoutParams = paramsTextView
        textView.text = tag
        textView.gravity = Gravity.CENTER

        return textView
    }

    private fun addFunSetOnclickListener(layoutTitle:View, layoutExpandable:View, button: Button){
        layoutTitle.setOnClickListener{
            if (layoutExpandable.visibility == View.GONE){
                layoutExpandable.visibility = View.VISIBLE
                button.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp)
            } else {
                layoutExpandable.visibility = View.GONE
                button.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp)
            }
        }
    }

    private fun convertFloatToDp(pixels:Float):Int{
        val dp = pixels
        val metrics: DisplayMetrics = this.resources.displayMetrics
        val fpixels = metrics.density * dp

        return (fpixels + 0.5f).toInt()
    }
}
