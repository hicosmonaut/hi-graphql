package hi.cosmonaut.graphql.ui.fragment

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
import hi.cosmonaut.graphql.data.model.ContinentList
import hi.cosmonaut.graphql.databinding.FragmentListContinentBinding
import hi.cosmonaut.graphql.viewmodel.ContinentsViewModel
import hi.cosmonaut.graphql.ui.adapter.ContinentListAdapter
import hi.cosmonaut.graphql.util.Constants

class ContinentListFragment: Fragment() {

    private lateinit var binding: FragmentListContinentBinding
    private lateinit var rvContinentList: RecyclerView
    //use activity view model to share requests and data
    private val sharedViewModel by activityViewModels<ContinentsViewModel>()
    private lateinit var continentListObserver: Observer<ContinentList>

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
        rvContinentList.adapter = ContinentListAdapter()
    }

    private fun initObservers() {
        if(Constants.LOGS_ENABLED) Log.d(TAG, "initObservers(): start")
        continentListObserver = Observer { continents ->
            if(Constants.LOGS_ENABLED) Log.d(TAG, "initObservers(): observer fired")
            val adapter = rvContinentList.adapter as ContinentListAdapter
            adapter.setContinentList(continents)
            adapter.setViewModel(sharedViewModel)
        }
    }

    override fun onResume() {
        super.onResume()
        if(Constants.LOGS_ENABLED) Log.d(TAG, "onResume(): start")
        startObserve()

    }

    private fun startObserve() {
        if(Constants.LOGS_ENABLED) Log.d(TAG, "startObserve(): start")
        sharedViewModel.getContinents().observe(viewLifecycleOwner, continentListObserver)
    }

    companion object{
        private val TAG: String = ContinentListFragment::class.java.simpleName
    }
}