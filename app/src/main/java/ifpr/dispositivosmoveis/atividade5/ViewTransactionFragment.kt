package ifpr.dispositivosmoveis.atividade5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ifpr.dispositivosmoveis.atividade5.adapters.RecordAdapter
import ifpr.dispositivosmoveis.atividade5.util.UserSession

class ViewTransactionFragment : Fragment(), View.OnClickListener {
    lateinit var adapter: RecordAdapter
    lateinit var editTextSearch: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var authUserId: Long = UserSession.getUserAuthId(requireActivity())

        var listTransactions = view.findViewById<RecyclerView>(R.id.listTransactions)

        context?.let {
            adapter = RecordAdapter(it, authUserId)
            listTransactions.adapter = adapter
            listTransactions.layoutManager = LinearLayoutManager(it, LinearLayoutManager.VERTICAL, false)
        }

        view.findViewById<Button>(R.id.btnSearch).setOnClickListener(this);
        editTextSearch = view.findViewById<EditText>(R.id.editTextSearch)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnSearch -> {
                var authUserId: Long = UserSession.getUserAuthId(requireActivity())
                adapter.search(authUserId, editTextSearch.text.toString())
            }
            else -> {
            }
        }
    }
}