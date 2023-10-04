package com.practicum.bluetoothbpr6

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.fragment.findNavController
import com.practicum.bluetoothbpr6.databinding.FragmentMainBinding
import com.practicum.bt_def.BluetoothConstants
import com.practicum.bt_def.bluetooth.BluetoothController

class MainFragment : Fragment(),BluetoothController.Listener {
    private lateinit var binding: FragmentMainBinding
    private lateinit var bluetoothController: BluetoothController
    private lateinit var btAdapter:BluetoothAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBtAdapter()
        val pref =
            activity?.getSharedPreferences(BluetoothConstants.PREFERENCES, Context.MODE_PRIVATE)
        val mac= pref?.getString(BluetoothConstants.MAC,"")
        bluetoothController = BluetoothController(btAdapter)
        binding.bList.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_deviceListFragment)
        }
        binding.bConnect.setOnClickListener{
            bluetoothController.connect(mac?:"",this)
            
        }
        binding.bSend.setOnClickListener{
            bluetoothController.sendMessage("AT+AB discovery all SPP\r")
            //bluetoothController.sendMessage("AT-AB-commandMode-")
            //bluetoothController.sendMessage("AT-AB 0080e1f00001")
//            bluetoothController.sendMessage("AT+AB GPIOConfig 1 O")
//            bluetoothController.sendMessage("AT-AB GPIO ConfigDone")
        }
    }
    private fun initBtAdapter() {
        val btManager = activity?.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        btAdapter = btManager.adapter
    }

    override fun onReceive(message: String) {
        activity?.runOnUiThread{
            when(message){
                BluetoothController.BLUETOOTH_CONNECTED->{
                    binding.bConnect.backgroundTintList = AppCompatResources.getColorStateList(requireContext(),R.color.red)
                    binding.bConnect.text = "Отключить bluetooth"
                }
                BluetoothController.BLUETOOTH_NO_CONNECTED->{
                    binding.bConnect.backgroundTintList = AppCompatResources.getColorStateList(requireContext(),R.color.green)
                    binding.bConnect.text = "Подключить bluetooth"
                }
                else->{
                    binding.tvConnectionStatus.text = message
                }
            }
        }
    }
}