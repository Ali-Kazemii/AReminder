package ir.awlrhm.reminder.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        handleError()
        handleObservers()
        handleOnClickListeners()
    }

    abstract fun setup()
    abstract fun handleObservers()
    abstract fun handleError()

    open fun handleOnClickListeners(){}
}