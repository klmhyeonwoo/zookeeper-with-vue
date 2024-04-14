import { api } from "../../../api/index";
import { useMutation } from "@tanstack/vue-query";
import useToastStore from "../../../stores/toast";

type registerNodeProps = {
  clusterName: string;
  value: string;
  searchQuery: string;
};
export const useApiRegisterNode = () => {
  const toastState = useToastStore();
  const { openToast } = toastState;
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
    onError: (err) => {
      openToast(false);
    },
  });
};
