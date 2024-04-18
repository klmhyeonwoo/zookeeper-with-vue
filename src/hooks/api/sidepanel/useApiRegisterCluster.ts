import { useMutation } from "@tanstack/vue-query";
import { api } from "../../../api/index";
import { storeToRefs } from "pinia";
import useGlobalState from "../../../stores/state";
import useToastStore from "../../../stores/toast";
import useModalStore from "../../../stores/modal";

type registerResponse = {
  host: string;
  name: string;
};

export const useApiRegisterCluster = () => {
  const globalState = useGlobalState();
  const { clusterData } = storeToRefs(globalState);
  const toastState = useToastStore();
  const { openToast } = toastState;
  const modalState = useModalStore();

  const registerCluster = async (address: string, name: string) => {
    const res = await api.post(
      `/api/zkui/clusters`,
      {
        host: address,
        name: name,
      },
      {
        headers: {
          "Content-Type": "application/json",
        },
      },
    );
    return res.data as registerResponse;
  };

  return useMutation({
    mutationFn: ({ address, name }: { address: string; name: string }) =>
      registerCluster(address, name),
    onSuccess: (res: registerResponse) => {
      const dataSet = [res.name, res.host];
      clusterData.value.push(dataSet);
      modalState.modalInfo.firstValue = "";
      modalState.modalInfo.twiceValue = "";
      openToast(true);
    },
    onError: (err) => {
      console.log("useApiRegisterCluster Fail:", err);
      openToast(false);
    },
  });
};
