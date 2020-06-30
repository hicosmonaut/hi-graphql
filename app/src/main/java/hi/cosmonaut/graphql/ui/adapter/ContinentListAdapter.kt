package hi.cosmonaut.graphql.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import hi.cosmonaut.graphql.R
import hi.cosmonaut.graphql.data.model.Continent
import hi.cosmonaut.graphql.data.model.ContinentList
import hi.cosmonaut.graphql.databinding.ItemContinentBinding
import hi.cosmonaut.graphql.viewmodel.ContinentsViewModel


class ContinentListAdapter: RecyclerView.Adapter<ContinentListAdapter.ViewHolder>() {

    private lateinit var context: Context
    private lateinit var continents: List<Continent>
    private lateinit var viewModel: ContinentsViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = ItemContinentBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    fun setContinentList(continentList: ContinentList) {
        this.continents = continentList.data.continents
        notifyDataSetChanged()
    }

    fun setViewModel(viewModel: ContinentsViewModel) {
        this.viewModel = viewModel
    }

    override fun getItemCount(): Int = if(::continents.isInitialized) continents.size else 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = continents.elementAt(position)
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: ItemContinentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Continent) {
            with(binding) {

                val normalLayoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

                root.layoutParams = normalLayoutParams

                //fill data
                itemContinentTvName.text = item.name
                itemContinentTvCode.text = item.code
                itemContinentTvNumberOfCountries.text = context.getString(R.string.template_number_of_countries, item.countries.size)

                //root click listener, user selects continent
                root.setOnClickListener {
                    it?.let{
                        if(::viewModel.isInitialized) viewModel.selectContinent(item)
                    }
                }
            }
        }
    }


    companion object{
        private val TAG: String = ContinentListAdapter::class.java.simpleName
    }
}