package hi.cosmonaut.graphql.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import hi.cosmonaut.graphql.R
import hi.cosmonaut.graphql.databinding.FragmentContentContinentBinding
import hi.cosmonaut.graphql.util.Constants

class ContinentContentFragment: Fragment() {

    //binding
    private lateinit var binding: FragmentContentContinentBinding

    //args
    private val args by navArgs<ContinentContentFragmentArgs>()

    //views
    private lateinit var tvContinentInfo: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_content_continent, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onResume() {
        super.onResume()
        setData(args)
    }

    private fun setData(args: ContinentContentFragmentArgs) {
        if(Constants.LOGS_ENABLED) Log.d(TAG, "setData(): start")
        tvContinentInfo.text = args.selectedContinent.toString()
    }

    private fun init(){
        if(Constants.LOGS_ENABLED) Log.d(TAG, "init(): start")
        initView()
    }

    private fun initView() {
        tvContinentInfo = binding.fragmentContentContinentTvTest
    }



    companion object{
        private val TAG: String = ContinentContentFragment::class.java.simpleName
    }
}