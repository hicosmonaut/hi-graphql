package hi.cosmonaut.graphql.ui.fragment.continent.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hi.cosmonaut.graphql.R
import hi.cosmonaut.graphql.data.model.Continent
import hi.cosmonaut.graphql.data.model.ContinentList
import hi.cosmonaut.graphql.databinding.FragmentListContinentBinding
import hi.cosmonaut.graphql.ui.fragment.home.HomeViewModel
import hi.cosmonaut.graphql.ui.adapter.ContinentListAdapter
import hi.cosmonaut.graphql.util.Constants

class ContinentListFragment: Fragment() {

    private lateinit var binding: FragmentListContinentBinding
    private lateinit var rvContinentList: RecyclerView

    //use activity view model to share requests and data
    private val sharedViewModel by activityViewModels<HomeViewModel>()
    private lateinit var continentListObserver: Observer<List<Continent>>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(Constants.LOGS_ENABLED) Log.d(TAG, "onCreateView(): start")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_continent, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(Constants.LOGS_ENABLED) Log.d(TAG, "onViewCreated(): start")
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        initViews()
        initComponents()
    }

    private fun initComponents() {
        initAdapter()
        initObservers()
    }

    private fun initViews() {

        if(Constants.LOGS_ENABLED) Log.d(TAG, "initViews(): start")

        rvContinentList = binding.fragmentListContinentRvContinents
        rvContinentList.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initAdapter() {
        if(Constants.LOGS_ENABLED) Log.d(TAG, "initAdapter(): start")

        val adapter = ContinentListAdapter().apply {
            setContinentList(mutableListOf())
            setViewModel(sharedViewModel)
        }

        rvContinentList.adapter = adapter

    }

    private fun initObservers() {
        if(Constants.LOGS_ENABLED) Log.d(TAG, "initObservers(): start")

        continentListObserver = Observer { continents ->
            if(Constants.LOGS_ENABLED) Log.d(TAG, "initObservers(): observer fired")
            continents?.let{ refreshData(it) }
        }
    }

    private fun refreshData(continents: List<Continent>) {
        getAdapter().setContinentList(continents)
    }

    private fun getAdapter() = rvContinentList.adapter as ContinentListAdapter

    override fun onStart() {
        super.onStart()
        startObserve()

    }

    override fun onResume() {
        super.onResume()
        if(Constants.LOGS_ENABLED) Log.d(TAG, "onResume(): start")
        sharedViewModel.fetchData()
    }

    private fun startObserve() {
        if(Constants.LOGS_ENABLED) Log.d(TAG, "startObserve(): start")
        sharedViewModel.continentsData.observe(viewLifecycleOwner, continentListObserver)
    }

    companion object{
        private val TAG: String = ContinentListFragment::class.java.simpleName
    }
}