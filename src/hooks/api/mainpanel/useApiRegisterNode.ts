import { api } from "../../../api/index";
import { useMutation } from "@tanstack/vue-query";
import useToastStore from "../../../stores/toast";
import useModalStore from "../../../stores/modal";
import { AxiosError } from "axios";

type registerNodeProps = {
  clusterName: string;
  value: string;
  searchQuery: string;
};

export const registerNode = async (
  clusterName: string,
  value: string,
  searchQuery: string,
  overwriteState: boolean = false,
) => {
  const res = await api.post(
    `/api/zkui/clusters/${clusterName}/node?path=${searchQuery}&overwrite=${overwriteState}`,
    value,
    {
      headers: {
        "Content-Type": "text/plain",
      },
    },
  );
  return res.data as registerNodeProps;
};
export const useApiRegisterNode = () => {
  const toastState = useToastStore();
  const { openToast } = toastState;
  const modalState = useModalStore();
  const { openModal } = modalState;

  return useMutation({
    mutationFn: ({ clusterName, value, searchQuery }: registerNodeProps) =>
      registerNode(clusterName, value, searchQuery),
    onSuccess(res: registerNodeProps) {
      openToast(true);
    },
    onError: (err: AxiosError) => {
      if (err.status === 409) {
        openModal("duplicateNodeData");
      } else {
        openToast(false);
      }
      throw new Error("new Error");
    },
  });
};
