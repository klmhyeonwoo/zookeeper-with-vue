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
export const useApiRegisterNode = () => {
  const toastState = useToastStore();
  const { openToast } = toastState;
  const modalState = useModalStore();
  const { openModal } = modalState;
  const registerNode = async (
    clusterName: string,
    value: string,
    searchQuery: string,
  ) => {
    const res = await api.post(
      `/api/zkui/clusters/${clusterName}/node?path=${searchQuery}`,
      value,
      {
        headers: {
          "Content-Type": "text/plain",
        },
      },
    );
    return res.data as registerNodeProps;
  };

  return useMutation({
    mutationFn: ({ clusterName, value, searchQuery }: registerNodeProps) =>
      registerNode(clusterName, value, searchQuery),
    onSuccess(res: registerNodeProps) {
      openToast(true);
    },
    onError: (err: typeof AxiosError) => {
      console.log("클러스터를 등록하는 도중 에러가 발생했습니다 :", err);
      if (err.status === 409 || err.status === 404) {
        openModal("duplicateNodeData");
      } else {
        openToast(false);
      }
    },
  });
};
