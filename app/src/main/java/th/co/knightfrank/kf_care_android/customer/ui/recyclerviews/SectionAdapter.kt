package th.co.knightfrank.kf_care_android.customer.ui.recyclerviews

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class SectionAdapter : RecyclerView.Adapter<SectionAdapter.ViewHolder>() {

    protected val items = mutableListOf<SectionData>()
    private val sectionTypes = mutableMapOf<Int, SectionViewAdapter<SectionData, ViewHolder>>()

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(vh: ViewHolder, position: Int) {
        val sectionViewAdapter = sectionTypes[getItemViewType(position)]
        sectionViewAdapter?.bindViewHolder(viewHolder = vh,
                item = items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
        val sectionViewAdapter = sectionTypes[viewType]
        if (parent != null) {
            return sectionViewAdapter?.createViewHolder(
                    layoutInflater = LayoutInflater.from(parent.context),
                    parent = parent)
        }
        return null
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].javaClass.hashCode()
    }

    fun <D : SectionData, VH : ViewHolder> addSectionType(cls: Class<out SectionData>,
                                                          sectionViewAdapter: SectionViewAdapter<D, VH>) {
        sectionTypes.put(cls.hashCode(), sectionViewAdapter as SectionViewAdapter<SectionData, ViewHolder>)
    }

    fun clear() {
        items.clear()
    }

    fun getListItems(): List<SectionData> = items.toList()

    fun addItem(item: SectionData) {
        items.add(item)
        notifyItemChanged(items.size - 1)
    }

    fun setItems(items: List<SectionData>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    interface SectionData

    interface SectionViewAdapter<in D : SectionData, VH : ViewHolder> {
        fun createViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup?): VH
        fun bindViewHolder(viewHolder: VH, item: D)
    }

    abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}