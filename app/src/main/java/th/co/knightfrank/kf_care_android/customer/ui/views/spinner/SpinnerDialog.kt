package th.co.knightfrank.kf_care_android.customer.ui.views.spinner


import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import th.co.knightfrank.kf_care_android.R

class SpinnerDialog
@JvmOverloads
constructor(
        context: Context,
        attributeSet: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0)
    : FrameLayout(context, attributeSet, defStyleAttr, defStyleRes), View.OnClickListener {

    private val choices: MutableList<Choice> = mutableListOf()
    private val selectedTextView: TextView
    private var innerSelectedIndex: Int = 0

    var confirmText: String = ""
    var title: String = ""
    var onSelectedChangeListener: OnSelectedChangeListener? = null
    var selectedIndex: Int
        get() = innerSelectedIndex
        set(value) {
            if (value >= 0 && value < choices.size) {
                innerSelectedIndex = value
            } else {
                innerSelectedIndex = 0
            }
            selectedTextView.text = choices[innerSelectedIndex].description
        }

    init {
        if (!isInEditMode) {
        }

        View.inflate(context, R.layout.view_spinner_dialog, this)

        selectedTextView = findViewById(R.id.selected_text)

        setOnClickListener(this)

        attributeSet?.let { attrs ->
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SpinnerDialog)
            val title = typedArray.getString(R.styleable.SpinnerDialog_dialog_title)
            val confirmText = typedArray.getString(R.styleable.SpinnerDialog_dialog_confirm)
            typedArray.recycle()

            this.title = title
            this.confirmText = confirmText
        }
    }

    fun setChoices(choices: List<Choice>) {
        this.choices.clear()
        this.choices.addAll(choices)
        innerSelectedIndex = 0
        if (choices.isEmpty()) {
            selectedTextView.text = ""
        } else {
            selectedTextView.text = choices.first().description
        }
    }

    override fun onClick(p0: View?) {
        MaterialDialog.Builder(context)
                .title(title)
                .items(choices.map { it.description })
                .itemsCallbackSingleChoice(innerSelectedIndex,
                        { _, _, which, text ->
                            selectedTextView.text = text
                            selectedIndex = which
                            onSelectedChangeListener?.onSelectedChange(
                                    choice = choices[which],
                                    index = which)
                            true
                        })
                .positiveText(confirmText)
                .show()
    }

    interface Choice {
        val description: String
    }

    public class SimpleChoice(val name: String) : Choice {
        override val description: String
            get() = name
    }

    interface OnSelectedChangeListener {
        fun onSelectedChange(choice: Choice, index: Int)
    }
}
