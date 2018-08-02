package com.alex.baking.app.ui.view.fragment;

//public abstract class BaseListFragment<ModelType extends BaseModel, PresenterType extends IPresenter> extends BaseFragment<List<ModelType>, PresenterType> {
//	private static final String TAG = BaseFragment.class.getSimpleName();
//  // TODO: 02/08/18 - Não comitar, dont push this
//	public BaseListFragment(PresenterType presenter) {
//		super(presenter);
//	}
//
//	protected abstract void createAdapter(List<ModelType> model);
//
//	protected abstract List<ModelType> destroyAdapter();
//
//	@Override
//	public void startView(List<ModelType> model) throws IllegalArgumentException {
//		Log.d(TAG, "Criando adapter com: " + model.size() + ". " + this.getClass().getSimpleName());
//		createAdapter(model);
//		Log.d(TAG, "Adapter criado");
//	}
//
//	@Override
//	public List<ModelType> destroyView(List<ModelType> model) {
//		Log.d(TAG, "Destruindo adapter" + ". " + this.getClass().getSimpleName());
//		model = destroyAdapter();
//		Log.d(TAG, "Adapter destruido");
//
//		return model;
//	}
//}
