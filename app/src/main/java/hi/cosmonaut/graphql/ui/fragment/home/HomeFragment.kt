package hi.cosmonaut.graphql.ui.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import hi.cosmonaut.graphql.R
import hi.cosmonaut.graphql.data.model.SelectedContinent
import hi.cosmonaut.graphql.databinding.FragmentContinentsBinding
import hi.cosmonaut.graphql.ui.fragment.continent.content.ContinentContentFragmentDirections
import hi.cosmonaut.graphql.util.Constants

class HomeFragment: Fragment(){
    private val viewModel by activityViewModels<HomeViewModel>()

    //binding
    private lateinit var binding: FragmentContinentsBinding

    //nav components
    private lateinit var navHostContentContinentFragment: View
    private lateinit var navControllerContentContinentFragment: NavController

    //viewmodel components
    private lateinit var selectedContinentObserver: Observer<SelectedContinent>
    private lateinit var messageObserver: Observer<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_continents, container, false)
        //container for list and content fragments
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        startObserve()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
    }

    private fun initComponents(){
        initObservers()
        initNavigationComponents()

        //put your other init methods below...
    }

    private fun initNavigationComponents() {
        navHostContentContinentFragment = binding.root.findViewById(R.id.fragment_continents_fr_container_continent_content)
        navControllerContentContinentFragment = Navigation.findNavController(navHostContentContinentFragment)
    }

    private fun initObservers() {
        selectedContinentObserver = Observer {
            it?.let{
                moveToSelectedContinent(it)
            }
        }

        messageObserver = Observer {
            it?.let{message ->
                showMessage(message)
            }
        }
    }

    private fun showMessage(message: String) {
        requireContext().let{
            Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
        }
    }

    //pass data to fragment
    //p.s in future if you need some requests in continent content fragment. You should implement your own logic
    private fun moveToSelectedContinent(continent: SelectedContinent) {
        val action = ContinentContentFragmentDirections.actionNavFragmentContentContinentsSelf()
        action.selectedContinent = continent
        performChildNavigation(action)
    }

    //child fragment container for navigation
    private fun performChildNavigation(action: NavDirections){
        navControllerContentContinentFragment.navigate(action)
    }

    //used in case of changing container in activity
    private fun performParentNavigation(action: NavDirections){
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        if(Constants.LOGS_ENABLED) Log.d(TAG, "onResume(): start")
    }

    private fun startObserve() {
        if(Constants.LOGS_ENABLED) Log.d(TAG, "startObserve(): start")
        viewModel.selectedContinentData.observe(this, selectedContinentObserver)
        viewModel.messageData.observe(this, messageObserver)
    }

    override fun onPause() {
        super.onPause()
        if(Constants.LOGS_ENABLED) Log.d(TAG, "onPause(): start")
    }

    override fun onDestroy() {
        super.onDestroy()
        if(Constants.LOGS_ENABLED) Log.d(TAG, "onDestroy(): start")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if(Constants.LOGS_ENABLED) Log.d(TAG, "onDestroyView(): start")
    }
    companion object{
        private val TAG: String = HomeFragment::class.java.simpleName
    }
}