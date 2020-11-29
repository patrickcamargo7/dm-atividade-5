package ifpr.dispositivosmoveis.atividade5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation

private const val ARG_RECIPIENT = "recipient"
private const val ARG_MONEY = "money"

class ConfirmationFragment : Fragment(), View.OnClickListener {
    lateinit var recipient: String
    lateinit var money: Money

    private var type: String? = null

    var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipient = requireArguments().getString("recipient")!!
        money = requireArguments().getParcelable("amount")!!
        type = requireArguments().getString("type")!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_confirmation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        val amount = money!!.amount

        view.findViewById<Button>(R.id.btnBacktohome).setOnClickListener(this);

        val tvConfirmationMessage = view.findViewById<TextView>(R.id.confirmation_message)

        tvConfirmationMessage.text = resources.getText(R.string.confirmation_message).toString().format(money.amount.toString(), recipient)

        if (type.equals(ChooseRecipientFragment.TYPE_RECEIVE)) {
            tvConfirmationMessage.text = resources.getText(R.string.confirmation_message_receive).toString().format(money.amount.toString(), recipient)
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnBacktohome -> {
                navController!!.navigate(R.id.mainFragment)
            }
        }
    }
}