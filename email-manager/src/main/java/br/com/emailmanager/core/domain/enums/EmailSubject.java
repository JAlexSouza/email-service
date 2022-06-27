package br.com.emailmanager.core.domain.enums;

public enum EmailSubject {
	
	VALIDATION{
		@Override
		public String getSubject() {
			return "Registration validation";
		}
	},
	WELCOME{
		@Override
		public String getSubject() {
			return "Welcome to our platform";
		}
	};
	
	public abstract String getSubject();
}
