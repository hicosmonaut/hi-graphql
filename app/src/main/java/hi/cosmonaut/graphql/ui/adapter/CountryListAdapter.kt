package hi.cosmonaut.graphql.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import hi.cosmonaut.graphql.data.model.Continent
import hi.cosmonaut.graphql.data.model.Country
import hi.cosmonaut.graphql.databinding.ItemCountryBinding
import hi.cosmonaut.graphql.ui.fragment.home.HomeViewModel

class CountryListAdapter(): RecyclerView.Adapter<CountryListAdapter.ViewHolder>() {

    private lateinit var context: Context
    private var countries: List<Country> = mutableListOf()
    private lateinit var viewModel: HomeViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = ItemCountryBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = countries.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }


    inner class ViewHolder(private val binding: ItemCountryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Continent) {
            with(binding) {
                configure(root)

                //fill data
                //itemContinentTvName.text = item.name
                //itemContinentTvCode.text = item.code
                //itemContinentTvNumberOfCountries.text = context.getString(R.string.template_number_of_countries, item.countries.size)

                //root click listener, user selects continent
                root.setOnClickListener {
                    it?.let{
                        if(::viewModel.isInitialized) viewModel.selectContinent(item)
                    }
                }
            }
        }

        private fun configure(root: View) {
            val normalLayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            root.layoutParams = normalLayoutParams
        }
    }


}