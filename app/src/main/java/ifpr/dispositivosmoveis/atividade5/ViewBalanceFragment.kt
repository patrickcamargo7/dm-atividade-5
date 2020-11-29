package ifpr.dispositivosmoveis.atividade5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ifpr.dispositivosmoveis.atividade5.database.AppDatabase
import ifpr.dispositivosmoveis.atividade5.database.dao.RecordDAO
import ifpr.dispositivosmoveis.atividade5.database.dao.UserDAO
import ifpr.dispositivosmoveis.atividade5.util.UserSession

class ViewBalanceFragment : Fragment() {

    private var dao: RecordDAO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_balance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dao = context?.let { AppDatabase.getInstance(it).recordDAO() }

        var userId: Long = UserSession.getUserAuthId(requireActivity())

        var receives: Float = dao!!.getTotalReceives(userId)
        var send: Float = dao!!.getTotalPayments(userId)
        var total: Float = send + receives;

        view.findViewById<TextView>(R.id.tvBalanceValue).text = "$total$"
        view.findViewById<TextView>(R.id.tvBalanceSend).text = """${(send * -1)}$"""
        view.findViewById<TextView>(R.id.tvBalanceReceive).text = "$receives$"

    }
}