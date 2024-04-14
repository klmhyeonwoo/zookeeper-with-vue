import { api } from "../../../api/index";
import { useMutation } from "@tanstack/vue-query";
import useToastStore from "../../../stores/toast";

type importResponse = {
  [index: string]: string;
};
export const useApiImportCluster = () => {
  const toastState = useToastStore();
  const { openToast } = toastState;
  const importCluster = async (
    clusterName: string,
    overwrite: boolean = false,
    data: object,
  ) => {
    const res = await api.post(
      `/api/zkui/clusters/${clusterName}/import`,
      {
        data,
      },
      {
        headers: {
          "Content-Type": "application/json",
        },
      },
    );
    return res.data as importResponse;
  };

  return useMutation({
    mutationFn: ({
      clusterName,
      overwrite,
      data,
    }: {
      clusterName: string;
      overwrite: boolean;
      data: object;
    }) => importCluster(clusterName, overwrite, data),
    onSuccess: () => {
      openToast(true);
    },
    onError(err) {
      console.log(err);
      openToast(false);
    },
  });
};
